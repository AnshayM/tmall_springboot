package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * IPropertyService
 *
 * @author: Anshay
 * @date: 2018/12/4
 */
public interface IPropertyService {

    /**
     * 添加
     *
     * @param property property
     * @return Property
     */
    Property add(Property property);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 查询单个实例
     *
     * @param id id
     * @return
     */
    Property get(Integer id);

    /**
     * 更新
     *
     * @param property property
     */
    void update(Property property);

    /**
     * 分页查询
     *
     * @param cid           categoryId
     * @param start         start
     * @param size          size
     * @param navigatePages navigatePages
     * @return Page4Navigator
     */
    Page4Navigator<Property> list(Integer cid, Integer start, Integer size, Integer navigatePages);

    /**
     * 根据分类查询所有属性集合
     *
     * @param category category
     * @return List<Property>
     */
    List<Property> listByCategory(Category category);
}
