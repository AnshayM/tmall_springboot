package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * ICategoryService
 *
 * @author: Anshay
 * @date: 2018/11/28
 */
public interface ICategoryService {
    /**
     * 获取category列表
     *
     * @return list
     */
    List<Category> list();

    /**
     * 分页查询
     *
     * @param start         start
     * @param size          size
     * @param navigatePages navigatePages
     * @return Page4Navigator
     */
    Page4Navigator<Category> list(Integer start, int size, int navigatePages);

}
