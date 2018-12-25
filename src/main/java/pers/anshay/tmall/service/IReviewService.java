package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Review;

import java.util.List;

/**
 * IReviewService
 *
 * @author: Anshay
 * @date: 2018/12/25
 */
public interface IReviewService {
    /**
     * add
     *
     * @param review review
     */
    void add(Review review);

    /**
     * list
     *
     * @param product product
     * @return List<Review>
     */
    List<Review> list(Product product);

    /**
     * getCount
     *
     * @param product
     * @return int
     */
    int getCount(Product product);
}
