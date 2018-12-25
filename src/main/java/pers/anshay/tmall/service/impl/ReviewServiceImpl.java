package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.ReviewDao;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.Review;
import pers.anshay.tmall.service.IReviewService;

import java.util.List;

/**
 * ReviewServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/25
 */
@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    ReviewDao reviewDao;

    @Override
    public void add(Review review) {
        reviewDao.save(review);
    }

    @Override
    public List<Review> list(Product product) {
        return reviewDao.findByProductOrderByIdDesc(product);
    }

    @Override
    public int getCount(Product product) {
        return reviewDao.countByProduct(product);
    }
}
