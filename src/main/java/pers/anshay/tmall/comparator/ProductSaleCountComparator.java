package pers.anshay.tmall.comparator;

import pers.anshay.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 销量比较器：把销量比较高的放在前面
 *
 * @author: Anshay
 * @date: 2018/12/26
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount() - p1.getSaleCount();
    }
}
