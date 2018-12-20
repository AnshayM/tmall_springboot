package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.anshay.tmall.pojo.Order;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IOrderService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.Result;

import java.util.Date;

/**
 * OrderController
 *
 * @author: Anshay
 * @date: 2018/12/19
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderItemService orderItemService;

    @GetMapping("/list")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                      @RequestParam(value = "size", defaultValue = "5") Integer size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, ConstantKey.NAVIGATE_PAGE_SIZE);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("/delivery/{orderId}")
    public Object deliveryOrder(@PathVariable Integer orderId) {
        Order order = orderService.get(orderId);
        order.setDeliveryDate(new Date());
        order.setStatus(ConstantKey.WAIT_CONFIRM);
        orderService.update(order);
        return Result.success();
    }
}
