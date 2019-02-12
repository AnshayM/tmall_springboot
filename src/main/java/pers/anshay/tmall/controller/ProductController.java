package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.Result;

import java.util.Date;

/**
 * ProductController
 *
 * @author: Anshay
 * @date: 2018/12/6
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;

    /**
     * 分页查询
     *
     * @param cid   categoryId
     * @param start start
     * @param size  size
     * @return Page4Navigator
     */
    @GetMapping("/{cid}/list")
    public Page4Navigator<Product> list(@PathVariable("cid") Integer cid, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                        @RequestParam(value = "size", defaultValue = "5") Integer size) {
        start = start > 0 ? start : 0;
        return productService.list(cid, start, size, ConstantKey.NAVIGATE_PAGE_SIZE);
    }

    /**
     * 添加
     *
     * @param product product
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Product product) {
        product.setCreateDate(new Date());
        Product bean = productService.add(product);
        return Result.success(bean);
    }

    /**
     * 删除
     *
     * @param id id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        productService.delete(id);
        return Result.success();
    }

    /**
     * 更新
     *
     * @param product product
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.success(product);
    }

    /**
     * 查询单个Product
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id) {
        Product product = productService.get(id);
        return Result.success(product);
    }
}
