package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/11/28
 */
@RestController
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @GetMapping("categories")
    public List<Category> list() {
        List list = categoryService.list();
        return list;
    }
}
