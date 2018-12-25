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

    @PostMapping("/foreRegister")
    public Object register(@RequestBody User user) {
        String name =  user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if(exist){
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);
        }

        user.setPassword(password);

        userService.add(user);

        return Result.success();
    }
}
