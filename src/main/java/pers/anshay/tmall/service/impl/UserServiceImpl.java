package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.UserDao;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IUserService;
import pers.anshay.tmall.util.Page4Navigator;

/**
 * UserServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserDao userDao;

    @Override
    public Page4Navigator<User> list(Integer start, Integer size, Integer navigatePages) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDao.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public boolean isExist(String name) {
        User user = getByName(name);
        return null != user;
    }

    @Override
    public User getByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public void add(User user) {
        userDao.save(user);
    }
}
