package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.ProductImage;

import java.util.List;

/**
 * ProductImageDao
 *
 * @author: Anshay
 * @date: 2018/12/8
 */
public interface ProductImageDao extends JpaRepository<ProductImage, Integer> {
    /**
     * 根据产品和分类查询产品图片
     *
     * @param product product
     * @param type    type
     * @return List<ProductImage>
     */
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
