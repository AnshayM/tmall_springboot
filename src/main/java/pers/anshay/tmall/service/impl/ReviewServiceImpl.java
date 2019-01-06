package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "reviews")
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    ReviewDao reviewDao;

    @Override
    @CacheEvict(allEntries = true)
    public void add(Review review) {
        reviewDao.save(review);
    }

    @Override
    @Cacheable(key = "'reviews-pid-'+ #p0.id")
    public List<Review> list(Product product) {
        return reviewDao.findByProductOrderByIdDesc(product);
    }

    @Override
    @Cacheable(key = "'reviews-count-pid-'+ #p0.id")
    public int getCount(Product product) {
        return reviewDao.countByProduct(product);
    }
}
