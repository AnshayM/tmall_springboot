package pers.anshay.tmall.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Product;

import java.util.List;

/**
 * ProductDao
 *
 * @author: Anshay
 * @date: 2018/12/6
 */
public interface ProductDao extends JpaRepository<Product, Integer> {
    /**
     * 分页查询分类下的产品
     *
     * @param category category
     * @param pageable pageable
     * @return Page
     */
    Page<Product> findByCategory(Category category, Pageable pageable);

    /**
     * 分页查询分类下的产品
     *
     * @param category category
     * @return List<Product>
     */
    List<Product> findByCategoryOrderById(Category category);

    /**
     * 根据关键字模糊搜索
     *
     * @param keyword  关键字
     * @param pageable 分页
     * @return List<Product>
     */
    List<Product> findByNameLike(String keyword, Pageable pageable);
}
