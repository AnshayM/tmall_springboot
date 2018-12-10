package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.ProductImage;

import java.util.List;

/**
 * IProductImageService
 *
 * @author: Anshay
 * @date: 2018/12/8
 */
public interface IProductImageService {
    /**
     * 增加
     *
     * @param productImage productImage
     */
    void add(ProductImage productImage);

    /**
     * 删除
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 更新
     *
     * @param productImage productImage
     */
    void update(ProductImage productImage);

    /**
     * 获取单个实例
     *
     * @param id id
     * @return
     */
    ProductImage get(Integer id);

    /**
     * 根据条件查询产品图片集合
     *
     * @param product product
     * @param type    single/detail
     * @return List<ProductImage>
     */
    List<ProductImage> listProductImage(Product product, String type);

    /**
     * 设置产品首图
     *
     * @param product product
     */
    void setFirstProductImage(Product product);

    /**
     * 初始化时批量设置首图
     *
     * @param products product集合
     */
    void setFirstProductImages(List<Product> products);
}
