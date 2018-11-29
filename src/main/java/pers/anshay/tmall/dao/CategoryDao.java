package pers.anshay.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.anshay.tmall.pojo.Category;

/**
 * CategoryDao
 *
 * @auth: machao
 * @date: 2018/11/28
 */
public interface CategoryDao extends JpaRepository<Category, Integer> {

}
