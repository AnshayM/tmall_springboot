package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.User;

import java.util.List;

/**
 * OrderItemDao
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
    /**
     * 根据订单查询订单项（根据Id降序排列）
     *
     * @param order order
     * @return List<OrderItem>
     */
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    /**
     * 根据产品获取OrderItem
     *
     * @param product product
     * @return List<OrderItem>
     */
    List<OrderItem> findByProduct(Product product);

    /**
     * 根据用户查询未生成订单的订单项集合
     *
     * @param user 用户
     * @return List<OrderItem>
     */
    List<OrderItem> findByUserAndOrderIsNull(User user);

}
