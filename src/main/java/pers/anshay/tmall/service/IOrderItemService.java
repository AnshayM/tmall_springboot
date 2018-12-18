package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;

import java.util.List;

/**
 * IOrderItemService
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
public interface IOrderItemService {

    void fill(List<Order> orders);

    void fill(Order order);

    List<OrderItem> listByOrder(Order order);
}
