package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/11/28
 */
@RestController
public class CategoryController {
    @Autowired
    ICategoryService categoryService;


    /* @GetMapping("categories")
     public List<Category> list() {
         List list = categoryService.list();
         return list;
     }*/

    /**
     * 分页查询分类列表
     *
     * @param start start
     * @param size  size
     * @return Page4Navigator
     */
    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        /*导航分页数。类似【1，2，3，4，5】这样*/
        Integer navigatePages = 5;
        Page4Navigator<Category> page = categoryService.list(start, size, navigatePages);
        return page;
    }
}
