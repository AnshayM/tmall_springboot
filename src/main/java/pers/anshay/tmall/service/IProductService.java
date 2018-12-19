package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.util.Page4Navigator;

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
}
