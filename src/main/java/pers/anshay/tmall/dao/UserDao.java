package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.User;

/**
 * UserDao
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
public interface UserDao extends JpaRepository<User, Integer> {
    /**
     * findByName
     *
     * @param name name
     * @return User
     */
    User findByName(String name);

    /**
     * 通过账号和密码查询账户
     *
     * @param name     name
     * @param password password
     * @return User
     */
    User getByNameAndPassword(String name, String password);

}
