package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.CategoryDao;
import pers.anshay.tmall.dao.PropertyDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Property;
import pers.anshay.tmall.service.IPropertyService;
import pers.anshay.tmall.util.Page4Navigator;

import java.util.List;

/**
 * PropertyServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/5
 */
@Service
public class PropertyServiceImpl implements IPropertyService {
    @Autowired
    PropertyDao propertyDao;
    @Autowired
    CategoryDao categoryDao;

    @Override
    public Property add(Property property) {
        return propertyDao.save(property);
    }

    @Override
    public void delete(Integer id) {
        propertyDao.delete(id);
    }

    @Override
    public Property get(Integer id) {
        return propertyDao.findOne(id);
    }

    @Override
    public void update(Property property) {
        propertyDao.save(property);
    }

    @Override
    public Page4Navigator<Property> list(Integer cid, Integer start, Integer size, Integer navigatePages) {

        Category category = categoryDao.getOne(cid);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);

        Page<Property> pageFromJPA = propertyDao.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);

    }

    @Override
    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
