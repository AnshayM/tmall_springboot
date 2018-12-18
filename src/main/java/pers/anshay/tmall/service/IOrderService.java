package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * IOrderService
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
public interface IOrderService {

    /*下面的方法返回值需要修改*/
    Page4Navigator<Order> list(Integer start, Integer size, Integer navigatePages);

    void removeOrderFromOrderItem(List<Order> orders);

    void removeOrderFromOrderItem(Order order);

    Order get(Integer orderId);

    void update(Order order);
}
