package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.TmResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * CategoryController
 *
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
        Page4Navigator<Category> page = categoryService.list(start, size, ConstantKey.NAVIGATE_PAGE_SIZE);
        return page;
    }

    /**
     * 添加新分类
     *
     * @param category category
     * @param image    image
     * @param request  request
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
    public TmResult deleteById(@PathVariable("id") Integer id, HttpServletRequest request) {
        TmResult result = new TmResult();

        categoryService.delete(id);

        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        result.setSuccess(true);
        result.setMessage("删除成功!");
        return result;
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

    /**
     * 更新category和相应图片(修改或者删除)
     *
     * @param category
     * @param image
     * @param request
     * @return TmResult
     * @throws IOException
     */
    @PutMapping("/{id}")
    public TmResult update(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        category.setName(name);
        categoryService.update(category);
        if (image != null) {
            categoryService.saveOrUpdateImageFile(category, image, request);
        }
        return new TmResult(true, "更新成功", category);
    }
}
