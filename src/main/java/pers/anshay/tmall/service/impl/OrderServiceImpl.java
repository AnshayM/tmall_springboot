package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.OrderDao;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.service.IOrderService;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    public Page4Navigator<Order> list(Integer start, Integer size, Integer navigatePages) {
        return null;
    }

    @Override
    public void removeOrderFromOrderItem(List<Order> orders) {

    }

    @Override
    public void removeOrderFromOrderItem(Order order) {

    }

    @Override
    public Order get(Integer orderId) {
        return null;
    }

    @Override
    public void update(Order order) {

    }
}
