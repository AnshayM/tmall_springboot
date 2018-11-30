package pers.anshay.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 后台管理页面控制器
 *
 * @author: Anshay
 * @date: 2018/11/28
 */
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "redirect:admin_category_list";
    }

    /**
     * 访问admin_category_list 就会访问admin/listCategory.html文件
     *
     * @return
     */
    @GetMapping("/admin_category_list")
    public String listCategory() {
        return "admin/listCategory";
    }
}
