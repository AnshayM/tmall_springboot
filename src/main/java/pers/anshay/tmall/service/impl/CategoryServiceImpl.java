package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.CategoryDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @author: Anshay
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

    @Override
    public Page4Navigator<Category> list(Integer start, int size, int navigatePages) {
        /*根据id升序排列*/
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = categoryDao.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
}
