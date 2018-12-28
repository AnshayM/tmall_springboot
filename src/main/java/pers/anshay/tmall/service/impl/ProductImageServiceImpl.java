package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.ProductImageDao;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.ProductImage;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.util.ConstantKey;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/12/8
 */
@Service
public class ProductImageServiceImpl implements IProductImageService {

    @Autowired
    ProductImageDao productImageDao;

    @Override
    public void add(ProductImage productImage) {
        productImageDao.save(productImage);
    }

    @Override
    public void delete(Integer id) {
        productImageDao.delete(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageDao.save(productImage);
    }

    @Override
    public ProductImage get(Integer id) {
        return productImageDao.findOne(id);
    }

    @Override
    public List<ProductImage> listProductImage(Product product, String type) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, type);
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> singleImages = listProductImage(product, ConstantKey.TYPE_SINGLE);
        if (!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            /*考虑到产品还没有设置图片时，但是在订单后台管理里要查看订单项的对应产品图片获取对应图片id为空
             * 这里设一个默认空图*/
            product.setFirstProductImage(new ProductImage());
        }

    }

    @Override
    public void setFirstProductImages(List<Product> products) {
        for (Product product : products) {
            setFirstProductImage(product);
        }
    }

    @Override
    public void serFirstProductImagesOnOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setFirstProductImage(orderItem.getProduct());
        }
    }

}
