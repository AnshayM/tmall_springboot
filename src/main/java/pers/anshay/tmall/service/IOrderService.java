package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.User;
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
     * 订单里的订单项的订单属性设置为空
     *
     * @param orders orders
     */
    void removeOrderFromOrderItem(List<Order> orders);

    /**
     * 订单里的订单项的订单属性设置为空，否则会无穷尽的查询
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

    /**
     * 创建订单
     *
     * @param order order
     */
    void add(Order order);

    /**
     * 创建订单并计算返回总价
     *
     * @param order      order
     * @param orderItems 订单项
     * @return 总价
     */
    float add(Order order, List<OrderItem> orderItems);

    /**
     * 查询用户订单（去除已删除订单）
     *
     * @param user user
     * @return List<Order>
     */
    List<Order> listByUserWithoutDelete(User user);

    /**
     * 查询用户订单（去除已删除订单）
     *
     * @param user user
     * @return List<Order>
     */
    List<Order> listByUserAndNotDeleted(User user);

    /**
     * 计算设置订单总额
     *
     * @param order order
     */
    void calcTotal(Order order);

}
