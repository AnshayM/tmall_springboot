package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.OrderItemDao;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.service.IProductService;

import java.util.List;

/**
 * OrderItemServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService {
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    IProductImageService productImageService;

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem item : orderItems) {
            total += item.getNumber() * item.getProduct().getPromotePrice();
            totalNumber += item.getNumber();
            productImageService.setFirstProductImage(item.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    @Override
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDao.findByOrderOrderByIdDesc(order);
    }

}
