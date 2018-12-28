package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.OrderItemDao;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IProductImageService;

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
    public void update(OrderItem orderItem) {
        orderItemDao.save(orderItem);
    }

    @Override
    public void add(OrderItem orderItem) {
        orderItemDao.save(orderItem);
    }

    @Override
    public OrderItem get(Integer id) {
        return orderItemDao.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        orderItemDao.delete(id);
    }

    @Override
    public Integer getSaleCount(Product product) {
        List<OrderItem> orderItems = listByProduct(product);
        int result = 0;
        // 这里可以考虑使用sql查询来做
        for (OrderItem orderItem : orderItems) {
            if (null != orderItem.getOrder()) {
                if (null != orderItem.getOrder().getPayDate()) {
                    result += orderItem.getNumber();
                }
            }
        }
        return result;
    }

    @Override
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDao.findByOrderOrderByIdDesc(order);
    }

    @Override
    public List<OrderItem> listByProduct(Product product) {
        return orderItemDao.findByProduct(product);
    }

    @Override
    public List<OrderItem> listByUser(User user) {
        return orderItemDao.findByUserAndOrderIsNull(user);
    }

}
