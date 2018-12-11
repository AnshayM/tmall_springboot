package pers.anshay.tmall.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Property;

import java.util.List;

/**
 * PropertyDao
 *
 * @author: Anshay
 * @date: 2018/12/4
 */
public interface PropertyDao extends JpaRepository<Property, Integer> {
    /**
     * 根据分类查询属性
     *
     * @param category category
     * @param pageable pageable
     * @return Page
     */
    Page<Property> findByCategory(Category category, Pageable pageable);

    /**
     * 根据分类查询所有属性集合
     *
     * @param category category
     * @return List<Property>
     */
    List<Property> findByCategory(Category category);

}
