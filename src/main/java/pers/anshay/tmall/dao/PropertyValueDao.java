package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * PropertyValueDao
 *
 * @author: Anshay
 * @date: 2018/12/11
 */
public interface PropertyValueDao extends JpaRepository<PropertyValue, Integer> {
    /**
     * findByProductOrderByIdDesc
     *
     * @param product product
     * @return List<PropertyValue>
     */
    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    /**
     * getByPropertyAndProduct
     *
     * @param Property Property
     * @param product  product
     * @return PropertyValue
     */
    PropertyValue getByPropertyAndProduct(Property Property, Product product);
}
