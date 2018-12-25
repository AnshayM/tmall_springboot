package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Review;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/12/25
 */
public interface ReviewDao extends JpaRepository<Review, Integer> {

    /**
     * 查询产品的评论
     *
     * @param product product
     * @return List<Review>
     */
    List<Review> findByProductOrderByIdDesc(Product product);

    /**
     * 查询产品的评论数
     *
     * @param product product
     * @return int
     */
    int countByProduct(Product product);
}
