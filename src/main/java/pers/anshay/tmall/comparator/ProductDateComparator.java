package pers.anshay.tmall.comparator;

import pers.anshay.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 新品比较器：把创建日期晚的放在前面
 *
 * @author: Anshay
 * @date: 2018/12/26
 */
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
