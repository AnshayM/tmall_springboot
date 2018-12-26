package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;

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
     * update
     *
     * @param orderItem orderItem
     */
    void update(OrderItem orderItem);

    /**
     * add
     *
     * @param orderItem orderItem
     */
    void add(OrderItem orderItem);

    /**
     * 通过id查询OrderItem
     *
     * @param id id
     * @return OrderItem
     */
    OrderItem get(Integer id);

    /**
     * delete
     *
     * @param id id
     */
    void delete(Integer id);

    /**
     * 获取产品销售量
     *
     * @param product product
     * @return Integer
     */
    Integer getSaleCount(Product product);

    /**
     * 查询order下的所有产品
     *
     * @param order order
     * @return List<OrderItem>
     */
    List<OrderItem> listByOrder(Order order);

    /**
     * listByProduct
     *
     * @param product product
     * @return List<OrderItem>
     */
    List<OrderItem> listByProduct(Product product);

}
