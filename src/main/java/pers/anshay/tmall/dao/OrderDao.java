package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Order;

/**
 * OrderDao
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
public interface OrderDao extends JpaRepository<Order, Integer> {
}
