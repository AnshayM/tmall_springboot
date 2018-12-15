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

}
