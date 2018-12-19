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

    /**
     * 分页查询
     *
     * @param start         start
     * @param size          size
     * @param navigatePages navigatePages
     * @return Page4Navigator
     */
    Page4Navigator<Order> list(Integer start, Integer size, Integer navigatePages);

    /**
     * 从订单中删除产品
     *
     * @param orders orders
     */
    void removeOrderFromOrderItem(List<Order> orders);

    /**
     * 从订单中删除产品
     *
     * @param order order
     */
    void removeOrderFromOrderItem(Order order);

    /**
     * 通过id获取订单
     *
     * @param orderId orderId
     * @return Order
     */
    Order get(Integer orderId);

    /**
     * 更新订单
     *
     * @param order order
     */
    void update(Order order);
}
