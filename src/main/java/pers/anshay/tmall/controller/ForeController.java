package pers.anshay.tmall.controller;

import org.apache.commons.lang.math.RandomUtils;
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
import java.text.SimpleDateFormat;
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
    @Autowired
    IOrderService orderService;

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

    @GetMapping("/foreCategory/{cid}")
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
            orderItem.setProduct(product);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            orderItemId = orderItem.getId();
        }
        return orderItemId;
    }

    /**
     * 结算
     *
     * @param oiid    订单项id数组
     * @param session session
     * @return 总额和订单集合
     */
    @GetMapping("/foreBuy")
    public Result buy(String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;

        for (String strId : oiid) {
            int id = Integer.valueOf(strId);
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }

        productImageService.serFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);

        Map<String, Object> map = new HashMap<>(2);
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
     * @return Result
     */
    @GetMapping("/foreAddCart")
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
    @GetMapping("/foreCart")
    public List<OrderItem> cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        productImageService.serFirstProductImagesOnOrderItems(orderItems);
        return orderItems;
    }

    /**
     * 修改订单项
     *
     * @param session session
     * @param pid     产品id
     * @param num     数量
     * @return Result
     */
    @GetMapping("/foreChangeOrderItem")
    public Result changeOrderItem(HttpSession session, Integer pid, Integer num) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }

        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == pid) {
                orderItem.setNumber(num);
                orderItemService.update(orderItem);
                break;
            }
        }
        return Result.success();
    }

    /**
     * 删除订单项
     *
     * @param session session
     * @param oiid    订单项id
     * @return Result
     */
    @GetMapping("/foreDeleteOrderItem")
    public Result deleteOrderItem(HttpSession session, Integer oiid) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        orderItemService.delete(oiid);
        return Result.success();
    }

    /**
     * 提交订单
     *
     * @param order   订单
     * @param session session
     * @return Result
     */
    @PostMapping("/foreCreateOrder")
    public Result createOder(@RequestBody Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(ConstantKey.WAIT_PAY);
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order, orderItems);

        Map map = new HashMap<>(2);
        map.put("oid", order.getId());
        map.put("total", total);
        return Result.success(map);
    }

    /**
     * 确认支付
     * 更新订单状态并返回订单信息
     *
     * @param orderId orderId
     * @return Order
     */
    @GetMapping("/forePayed")
    public Order payed(Integer orderId) {
        Order order = orderService.get(orderId);
        order.setStatus(ConstantKey.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }

    /**
     * 我的订单
     *
     * @param session session
     * @return Result
     */
    @GetMapping("/foreBought")
    public Result bought(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        List<Order> orders = orderService.listByUserWithoutDelete(user);
        orderService.removeOrderFromOrderItem(orders);
        return Result.success(orders);
    }

    /**
     * 确认收货（待重构）
     *
     * @param oid orderId
     * @return Result
     */
    @GetMapping("/foreConfirmPay")
    public Result confirmPay(Integer oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        orderService.calcTotal(order);
        orderService.removeOrderFromOrderItem(order);
        return Result.success(order);
    }

    /**
     * 确认收货成功
     *
     * @param oid orderId
     * @return Result
     */
    @GetMapping("/foreOrderConfirmed")
    public Result orderConfirmed(Integer oid) {
        Order order = orderService.get(oid);
        order.setStatus(ConstantKey.WAIT_REVIEW);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return Result.success();
    }

    /**
     * 删除订单
     *
     * @param oid orderId
     * @return Result
     */
    @PutMapping("/foreDeleteOrder")
    public Result deleteOrder(Integer oid) {
        Order order = orderService.get(oid);
        order.setStatus(ConstantKey.DELETE);
        orderService.update(order);
        return Result.success();
    }

    /**
     * 查询评论列表（带重构）
     *
     * @param oid orderId
     * @return Result
     */
    @GetMapping("/foreReview")
    public Result review(Integer oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        orderService.removeOrderFromOrderItem(order);
        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(product);
        Map map = new HashMap(3);
        map.put("p", product);
        map.put("o", order);
        map.put("reviews", reviews);

        return Result.success(map);
    }

    /**
     * 提交评价
     *
     * @param session session
     * @param oid     订单id
     * @param pid     产品id
     * @param content 评价内容
     * @return Result
     */
    @GetMapping("/foreDoReview")
    public Result doReview(HttpSession session, Integer oid, Integer pid, String content) {
        Order order = orderService.get(oid);
        order.setStatus(ConstantKey.FINISH);
        orderService.update(order);

        Product product = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(product);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewService.add(review);

        return Result.success();
    }

}
