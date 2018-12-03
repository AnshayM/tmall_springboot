package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.SimpleResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author: Anshay
 * @date: 2018/11/28
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    /**
     * 分页查询分类列表
     *
     * @param start start
     * @param size  size
     * @return Page4Navigator
     */
    @GetMapping("/list")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        /*导航分页数。类似【1，2，3，4，5】这样*/
        Integer navigatePages = 5;
        Page4Navigator<Category> page = categoryService.list(start, size, navigatePages);
        return page;
    }

    /**
     * 添加新分类
     *
     * @param category
     * @param image
     * @param request
     * @throws IOException
     */
    @PostMapping("/add")
    public void add(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
        categoryService.add(category);
        /*添加图片信息*/
        categoryService.saveOrUpdateImageFile(category, image, request);
    }

    /**
     * 根据id删除分类，并删除对应图片
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        SimpleResponse response = new SimpleResponse();

        categoryService.delete(id);

        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        response.setStatus(SimpleResponse.SUCCESS);
        response.setMessage("删除成功!");
        return response;
    }

    /**
     * 根据id获取分类实体
     *
     * @param id id
     * @return Category
     */
    @GetMapping("/{id}")
    public Category get(@PathVariable("id") Integer id) {
        return categoryService.get(id);
    }
}
