package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.User;

import java.util.List;

/**
 * OrderDao
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
public interface OrderDao extends JpaRepository<Order, Integer> {
    /**
     * 查询用户订单列表（待重构：为什么要加NotOrderByIdDesc）
     *
     * @param user   user
     * @param status status
     * @return List<Order>
     */
    List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}
