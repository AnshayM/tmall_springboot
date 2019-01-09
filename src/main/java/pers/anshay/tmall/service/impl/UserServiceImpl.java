package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.UserDao;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IUserService;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.SpringContextUtil;

/**
 * UserServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserDao userDao;

    @Override
    @Cacheable(key = "'users-page-'+#p0+ '-' + #p1")
    public Page4Navigator<User> list(Integer start, Integer size, Integer navigatePages) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDao.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public boolean isExist(String name) {
        IUserService userService = SpringContextUtil.getBean(IUserService.class);
        User user = userService.getByName(name);
        return null != user;
    }

    @Override
    @Cacheable(key = "'users-one-name-'+ #p")
    public User getByName(String name) {
        return userDao.findByName(name);
    }

    @Override

    @CacheEvict(allEntries = true)
    public void add(User user) {
        userDao.save(user);
    }

    @Override
    @Cacheable(key = "'users-one-name-'+ #p0 +'-password-'+ #p1")
    public User get(String name, String password) {
        return userDao.getByNameAndPassword(name, password);
    }
}
