package pers.anshay.tmall.comparator;

import pers.anshay.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 人气比较器：把评论多的放在前面
 *
 * @author: Anshay
 * @date: 2018/12/26
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() - p1.getReviewCount();
    }
}
