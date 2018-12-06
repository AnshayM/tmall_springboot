package pers.anshay.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pers.anshay.tmall.dao.CategoryDao;
import pers.anshay.tmall.dao.ProductDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.util.Page4Navigator;

/**
 * ProductServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/6
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductDao productDao;

    @Override
    public Product add(Product product) {
        return productDao.save(product);
    }

    @Override
    public void delete(Integer id) {
        productDao.delete(id);
    }

    @Override
    public Product get(Integer id) {
        return productDao.getOne(id);
    }

    @Override
    public Product update(Product product) {
        return productDao.save(product);
    }

    @Override
    public Page4Navigator<Product> list(Integer cid, Integer start, Integer size, Integer navigatePages) {
        Category category = categoryDao.getOne(cid);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Product> pageFromJPA = productDao.findByCategory(category, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
}
