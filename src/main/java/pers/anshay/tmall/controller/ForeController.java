package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.service.IUserService;
import pers.anshay.tmall.util.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ForeRESTController
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

    /**
     * 登出，跳转到home页面
     *
     * @param session session
     * @return String
     */
    @GetMapping("/foreLogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }
}
