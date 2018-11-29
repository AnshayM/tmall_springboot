package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.CategoryDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;

import java.util.List;

/**
 * @auth: machao
 * @date: 2018/11/28
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDao.findAll(sort);
    }
}
