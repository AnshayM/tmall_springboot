package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.OrderItemDao;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.util.SpringContextUtil;

import javax.swing.*;
import java.util.List;

/**
 * OrderItemServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
@Service
@CacheConfig(cacheNames = "orderItems")
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
        IOrderItemService orderItemService = SpringContextUtil.getBean(IOrderItemService.class);
        List<OrderItem> orderItems = orderItemService.listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem item : orderItems) {
            total += item.getNumber() * item.getProduct().getPromotePrice();
            totalNumber += item.getNumber();
            productImageService.setFirstProductImage(item.getProduct());
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(OrderItem orderItem) {
        orderItemDao.save(orderItem);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void add(OrderItem orderItem) {
        orderItemDao.save(orderItem);
    }

    @Override
    @Cacheable(key = "'orderItems-one-'+ #p0")
    public OrderItem get(Integer id) {
        return orderItemDao.findOne(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        orderItemDao.delete(id);
    }

    @Override
    public Integer getSaleCount(Product product) {
        IOrderItemService orderItemService = SpringContextUtil.getBean(IOrderItemService.class);
        List<OrderItem> orderItems = orderItemService.listByProduct(product);
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
    @Cacheable(key = "'orderItems-oid-'+ #p0.id")
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDao.findByOrderOrderByIdDesc(order);
    }

    @Override
    @Cacheable(key = "'orderItems-pid-'+ #p0.id")
    public List<OrderItem> listByProduct(Product product) {
        return orderItemDao.findByProduct(product);
    }

    @Override
    @Cacheable(key = "'orderItems-uid-'+ #p0.id")
    public List<OrderItem> listByUser(User user) {
        return orderItemDao.findByUserAndOrderIsNull(user);
    }

}
