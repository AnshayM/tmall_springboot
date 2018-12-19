package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.PropertyValue;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.service.IPropertyValueService;
import pers.anshay.tmall.util.TmResult;

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
    public TmResult list(@PathVariable("pid") Integer pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        return new TmResult(true, "查询成功", propertyValues);
    }

    @PutMapping("/update")
    public TmResult update(@RequestBody PropertyValue propertyValue) {
        PropertyValue bean = propertyValueService.update(propertyValue);
        return new TmResult(true, "更新成功", bean);
    }
}
