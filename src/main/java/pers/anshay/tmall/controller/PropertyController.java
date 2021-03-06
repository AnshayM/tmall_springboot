package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.service.IPropertyService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.Result;

/**
 * @author: Anshay
 * @date: 2018/12/5
 */
@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    IPropertyService propertyService;

    /**
     * 根据cid分页查询属性
     *
     * @param cid   cid
     * @param start start
     * @param size  size
     * @return Page4Navigator<Property>
     */
    @GetMapping("/{cid}/list")
    public Page4Navigator<Property> list(@PathVariable("cid") Integer cid, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Property> page = propertyService.list(cid, start, size, ConstantKey.NAVIGATE_PAGE_SIZE);
        return page;
    }

    /**
     * 添加
     *
     * @param property property
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Property property) {
        Property bean = propertyService.add(property);
        return Result.success(bean);
    }

    /**
     * 删除
     *
     * @param id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        propertyService.delete(id);
        return Result.success(id);
    }

    /**
     * 更新
     *
     * @param property
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Property property) {
        propertyService.update(property);
        return Result.success(property);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id) {
        Property property = propertyService.get(id);
        return Result.success(property);
    }

}
