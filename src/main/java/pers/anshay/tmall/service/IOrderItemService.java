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

    /**
     * 填充订单下产品信息
     *
     * @param orders orders
     */
    void fill(List<Order> orders);

    /**
     * 填充订单下产品信息
     *
     * @param order order
     */
    void fill(Order order);

    /**
     * 查询ordere下的所有产品
     *
     * @param order order
     * @return List<OrderItem>
     */
    List<OrderItem> listByOrder(Order order);
}
