package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.anshay.tmall.dao.OrderDao;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IOrderService;
import pers.anshay.tmall.util.ConstantKey;
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
    @Autowired
    IOrderItemService orderItemService;

    @Override
    public Page4Navigator<Order> list(Integer start, Integer size, Integer navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = orderDao.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }

    }

    @Override
    public void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    @Override
    public Order get(Integer orderId) {
        return orderDao.findOne(orderId);
    }

    @Override
    public void update(Order order) {
        orderDao.save(order);
    }

    @Override
    public void add(Order order) {
        orderDao.save(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        orderDao.save(order);
        if (false) {
            // 测试创建订单时抛出异常情况
            throw new RuntimeException();
        }
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = orderDao.findByUserAndStatusNotOrderByIdDesc(user, ConstantKey.DELETE);
        orderItemService.fill(orders);
        return orders;
    }

    @Override
    public void calcTotal(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        order.setTotal(total);
    }
}
