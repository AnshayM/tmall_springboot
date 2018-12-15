package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.util.Page4Navigator;

/**
 * IUserService
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
public interface IUserService {
    /**
     * 分页查询用户列表
     *
     * @param start         start
     * @param size          size
     * @param navigatePages navigatePages
     * @return Page4Navigator<User>
     */
    Page4Navigator<User> list(Integer start, Integer size, Integer navigatePages);
}
