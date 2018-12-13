package pers.anshay.tmall.service;

import org.elasticsearch.action.fieldstats.IndexConstraint;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/12/12
 */
public interface IPropertyValueService {
    /**
     * 初始化PropertyValue
     * PropertyValue未设置增加，只有修改。所以通过初始化来进行自动增加。
     *
     * @param product product
     */
    void init(Product product);

    /**
     * getByPropertyAndProduct
     *
     * @param product  product
     * @param property property
     * @return PropertyValue
     */
    PropertyValue getByPropertyAndProduct(Product product, Property property);

    /**
     * 根据产品查询所有属性值
     *
     * @param product
     * @return List<PropertyValue>
     */
    List<PropertyValue> list(Product product);

    /**
     * 更新
     *
     * @param propertyValue propertyValue
     * @return PropertyValue
     */
    PropertyValue update(PropertyValue propertyValue);
}
