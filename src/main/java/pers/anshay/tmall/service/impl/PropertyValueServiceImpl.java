package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.PropertyValueDao;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.pojo.PropertyValue;
import pers.anshay.tmall.service.IPropertyService;
import pers.anshay.tmall.service.IPropertyValueService;

import java.util.List;

/**
 * PropertyValueServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/12
 */
@Service
public class PropertyValueServiceImpl implements IPropertyValueService {

    @Autowired
    PropertyValueDao propertyValueDao;
    @Autowired
    IPropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.listByCategory(product.getCategory());
        for (Property property : properties) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDao.save(propertyValue);
            }
        }
    }

    @Override
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueDao.getByPropertyAndProduct(property, product);
    }

    @Override
    public List<PropertyValue> list(Product product) {
        return propertyValueDao.findByProductOrderByIdDesc(product);
    }

    @Override
    public PropertyValue update(PropertyValue propertyValue) {
        return propertyValueDao.save(propertyValue);
    }
}
