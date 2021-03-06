package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.ProductImageDao;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.ProductImage;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.SpringContextUtil;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/12/8
 */
@Service
@CacheConfig(cacheNames = "productImages")
public class ProductImageServiceImpl implements IProductImageService {

    @Autowired
    ProductImageDao productImageDao;

    @Override
    @CacheEvict(allEntries = true)
    public void add(ProductImage productImage) {
        productImageDao.save(productImage);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        productImageDao.delete(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(ProductImage productImage) {
        productImageDao.save(productImage);
    }

    @Override
    @Cacheable(key = "'productImages-one-'+ #p0")
    public ProductImage get(Integer id) {
        return productImageDao.findOne(id);
    }

    @Override
    @Cacheable(key = "'productImages-pid-'+ #p0.id+'-type-'+#p1")
    public List<ProductImage> listProductImage(Product product, String type) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, type);
    }

    @Override
    @Cacheable(key = "'productImages-single-pid-'+ #p0.id")
    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, ConstantKey.TYPE_SINGLE);
    }

    @Override
    @Cacheable(key = "'productImages-detail-pid-'+ #p0.id")
    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, ConstantKey.TYPE_DETAIL);
    }

    @Override
    public void setFirstProductImage(Product product) {
        IProductImageService productImageService = SpringContextUtil.getBean(IProductImageService.class);
        List<ProductImage> singleImages = productImageService.listProductImage(product, ConstantKey.TYPE_SINGLE);
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
