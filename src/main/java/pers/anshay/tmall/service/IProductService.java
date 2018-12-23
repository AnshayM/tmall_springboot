package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/12/6
 */
public interface IProductService {
    /**
     * 添加
     *
     * @param product product
     * @return TmResult
     */
    Product add(Product product);

    /**
     * 删除
     *
     * @param id id
     * @return TmResult
     */
    void delete(Integer id);

    /**
     * 根据id查询产品
     *
     * @param id id
     * @return TmResult
     */
    Product get(Integer id);

    /**
     * 更新
     *
     * @param product product
     * @return TmResult
     */
    Product update(Product product);

    /**
     * 分页查询
     *
     * @param cid           categoryId
     * @param start         start
     * @param size          size
     * @param navigatePages navigatePages
     * @return Page4Navigator
     */

    Page4Navigator<Product> list(Integer cid, Integer start, Integer size, Integer navigatePages);

    /**
     * 为分类填充产品集合
     *
     * @param category category
     */
    void fill(Category category);

    /**
     * 为分类填充产品集合
     *
     * @param categories categories
     */
    void fill(List<Category> categories);

    /**
     * 为多个分类填充推荐产品集合，
     * 即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
     *
     * @param categories categories
     */
    void fillByRow(List<Category> categories);

    /**
     * 查询某个分类下的所有产品
     *
     * @param category category
     * @return List<Product>
     */
    List<Product> listByCategory(Category category);
}
