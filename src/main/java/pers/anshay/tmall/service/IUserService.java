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

    /**
     * 判断某个姓名的用户是否已经存在
     *
     * @param name
     * @return
     */
    boolean isExist(String name);

    /**
     * getByName
     *
     * @param name name
     * @return
     */
    User getByName(String name);

    /**
     * 添加用户
     *
     * @param user user
     */
    void add(User user);
}
