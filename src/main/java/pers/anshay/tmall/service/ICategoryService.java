package pers.anshay.tmall.service;

import pers.anshay.tmall.pojo.Category;

import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/11/28
 */
public interface ICategoryService {
    /**
     * 获取category列表
     *
     * @return list
     */
    public List<Category> list();
}
