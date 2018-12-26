package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import pers.anshay.tmall.pojo.*;
import pers.anshay.tmall.service.*;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Result;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/foreHome")
    public Object home() {
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

        Map<String, Object> map = new HashMap<>();
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

}
