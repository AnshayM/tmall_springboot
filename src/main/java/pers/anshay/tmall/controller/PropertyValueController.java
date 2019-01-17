package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.PropertyValue;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.service.IPropertyValueService;
import pers.anshay.tmall.util.Result;

import java.util.List;

/**
 * PropertyValueController
 *
 * @author: Anshay
 * @date: 2018/12/12
 */
@RestController
@RequestMapping("/propertyValue")
public class PropertyValueController {
    @Autowired
    IPropertyValueService propertyValueService;
    @Autowired
    IProductService productService;

    @GetMapping("/{pid}/list")
    public Result list(@PathVariable("pid") Integer pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        return Result.success(propertyValues);
    }

    @PutMapping("/update")
    public Result update(@RequestBody PropertyValue propertyValue) {
        PropertyValue bean = propertyValueService.update(propertyValue);
        return Result.success(bean);
    }
}
