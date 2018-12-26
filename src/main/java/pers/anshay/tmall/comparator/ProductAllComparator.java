package pers.anshay.tmall.comparator;

import pers.anshay.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 综合比较器：把 销量*评价 高的放在前面
 *
 * @author: Anshay
 * @date: 2018/12/26
 */
public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
    }
}
