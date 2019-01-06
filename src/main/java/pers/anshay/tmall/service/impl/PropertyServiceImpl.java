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
@CacheConfig(cacheNames = "properties")
public class PropertyServiceImpl implements IPropertyService {
    @Autowired
    PropertyDao propertyDao;
    @Autowired
    CategoryDao categoryDao;

    @Override
    @CacheEvict(allEntries = true)
    public Property add(Property property) {
        return propertyDao.save(property);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        propertyDao.delete(id);
    }

    @Override
    @Cacheable(key = "'properties-one-'+ #p0")
    public Property get(Integer id) {
        return propertyDao.findOne(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(Property property) {
        propertyDao.save(property);
    }

    @Override
    @Cacheable(key = "'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Property> list(Integer cid, Integer start, Integer size, Integer navigatePages) {

        Category category = categoryDao.getOne(cid);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);

        Page<Property> pageFromJPA = propertyDao.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);

    }

    @Override
    @Cacheable(key = "'properties-cid-'+ #p0.id")
    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
