package pers.anshay.tmall.controller;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import pers.anshay.tmall.comparator.*;
import pers.anshay.tmall.pojo.*;
import pers.anshay.tmall.service.*;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Result;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 前台请求处理器
 *
 * @author: Anshay
 * @date: 2018/12/20
 */
@RestController
public class ForeController {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IProductService productService;
    @Autowired
    IUserService userService;
    @Autowired
    IProductImageService productImageService;
    @Autowired
    IPropertyValueService propertyValueService;
    @Autowired
    IReviewService reviewService;
    @Autowired
    IOrderItemService orderItemService;

    /**
     * 首页
     *
     * @return List<Category>
     */
    @GetMapping("/foreHome")
    public List<Category> home() {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        categoryService.removeCategoryFromProduct(categories);
        return categories;
    }

    /**
     * 注册
     *
     * @param user user
     * @return Result
     */
    @PostMapping("/foreRegister")
    public Result register(@RequestBody User user) {
        String name = HtmlUtils.htmlEscape(user.getName());
        String password = user.getPassword();
        user.setName(name);
        if (userService.isExist(name)) {
            String message = "用户名已经被使用,不能使用";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }

    /**
     * 登录
     *
     * @param userParam userParam
     * @param session   session
     * @return Result
     */
    @PostMapping("/foreLogin")
    public Result login(@RequestBody User userParam, HttpSession session) {
        String name = HtmlUtils.htmlEscape(userParam.getName());
        User user = userService.get(name, userParam.getPassword());
        if (user == null) {
            return Result.fail("账号密码错误");
        } else {
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    /**
     * 产品页
     *
     * @param pid pid
     * @return Result
     */
    @GetMapping("/foreProduct/{pid}")
    public Result product(@PathVariable("pid") Integer pid) {
        Product product = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.listProductImage(product, ConstantKey.TYPE_SINGLE);
        List<ProductImage> productDetailImages = productImageService.listProductImage(product, ConstantKey.TYPE_DETAIL);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> propertyValues = propertyValueService.list(product);
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String, Object> map = new HashMap<>(3);
        map.put("product", product);
        map.put("pvs", propertyValues);
        map.put("reviews", reviews);
        return Result.success(map);
    }

    @GetMapping("/foreCheckLogin")
    public Result checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return Result.success();
        }
        return Result.fail("未登录");
    }

    @GetMapping("foreCategory/{cid}")
    private Object category(@PathVariable Integer cid, String sort) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());
        categoryService.removeCategoryFromProduct(category);

        if (!StringUtil.isEmpty(sort)) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                default:
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
            }
        }
        return category;
    }

    /**
     * 产品模糊搜索
     *
     * @param keyword keyword
     * @param start   start
     * @param size    size
     * @return List<Product>
     */
    @PostMapping("/foreSearch")
    public List<Product> search(String keyword, Integer start, Integer size) {
        start = start == null ? 0 : start;
        size = size == null ? 20 : size;
        keyword = keyword == null ? "" : keyword;
        List<Product> products = productService.search(keyword, start, size);
        productImageService.setFirstProductImages(products);
        productService.setSaleAndReviewNumber(products);
        return products;
    }

    /**
     * 点击立即购买后进行的订单操作
     *
     * @param pid     产品id
     * @param num     数量
     * @param session session
     * @return 订单项id
     */
    @GetMapping("/foreBuyOne")
    public int buyOne(Integer pid, Integer num, HttpSession session) {
        return buyOneAndAddCart(pid, num, session);
    }

    /**
     * 添加订单
     *
     * @param pid     产品id
     * @param num     数量
     * @param session session
     * @return 订单项id
     */
    private int buyOneAndAddCart(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int orderItemId = 0;
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == product.getId()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                orderItemId = orderItem.getId();
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            orderItemId = orderItem.getId();
        }
        return orderItemId;
    }

    /**
     * 结算
     *
     * @param orderItemIds 订单项id数组
     * @param session      session
     * @return 总额和订单集合
     */
    @GetMapping("foreBuy")
    public Result buy(String[] orderItemIds, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;

        for (String strId : orderItemIds) {
            int id = Integer.valueOf(strId);
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }

        productImageService.serFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);

        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);

        return Result.success(map);
    }

    /**
     * 添加到购物车
     *
     * @param pid     产品id
     * @param num     数量
     * @param session session
     * @return
     */
    @GetMapping("foreAddCart")
    public Result addCart(Integer pid, Integer num, HttpSession session) {
        buyOneAndAddCart(pid, num, session);
        return Result.success();
    }

    /**
     * 查看购物车
     *
     * @param session session
     * @return List<OrderItem>
     */
    @GetMapping("foreCart")
    public List<OrderItem> cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        return orderItems;
    }

}
