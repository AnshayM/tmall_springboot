package pers.anshay.tmall.comparator;

import pers.anshay.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 价格比较器：把价格低的放在前面
 *
 * @author: Anshay
 * @date: 2018/12/26
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }
}
