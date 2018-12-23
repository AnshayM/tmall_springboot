package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.service.IProductService;

import java.util.List;

/**
 * ForeRESTController
 *
 * @author: Anshay
 * @date: 2018/12/20
 */
@RestController
public class ForeRESTController {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IProductService productService;

    @GetMapping("/foreHome")
    public Object home() {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        categoryService.removeCategoryFromProduct(categories);
        return categories;
    }
}
