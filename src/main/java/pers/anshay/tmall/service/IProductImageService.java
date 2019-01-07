package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.OrderItem;
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

    List<ProductImage> listSingleProductImages(Product product);

    List<ProductImage> listDetailProductImages(Product product);

    /**
     * 设置产品首图
     *
     * @param product product
     */
    void setFirstProductImage(Product product);

    /**
     * 初始化时批量设置首图（如果产品为设置首图，订单管理查阅图片时会出错，后期加一个判断，如果为null，就指配默认图片）
     *
     * @param products product集合
     */
    void setFirstProductImages(List<Product> products);

    /**
     * 为订单项里每个产品设置首图（防止加载出错）
     *
     * @param orderItems 订单项集合
     */
    void serFirstProductImagesOnOrderItems(List<OrderItem> orderItems);
}
