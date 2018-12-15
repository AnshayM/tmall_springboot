package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IUserService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;

/**
 * UserController
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    /**
     * 分页查询
     *
     * @param start start
     * @param size  size
     * @return Page4Navigator<User>
     */
    @GetMapping("/list")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list(start, size, ConstantKey.NAVIGATE_PAGE_SIZE);
        return page;
    }
}

