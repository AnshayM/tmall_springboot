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
import pers.anshay.tmall.dao.ProductDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.service.IOrderItemService;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.service.IReviewService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.Page4Navigator;
import pers.anshay.tmall.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductServiceImpl
 *
 * @author: Anshay
 * @date: 2018/12/6
 */
@Service
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements IProductService {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    IProductImageService productImageService;
    @Autowired
    IOrderItemService orderItemService;
    @Autowired
    IReviewService reviewService;

    @Override
    @CacheEvict(allEntries = true)
    public Product add(Product product) {
        return productDao.save(product);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        productDao.delete(id);
    }

    @Override
    @Cacheable(key = "'products-one-'+#p0")
    public Product get(Integer id) {
        return productDao.getOne(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Product update(Product product) {
        return productDao.save(product);
    }

    @Override
    @Cacheable(key = "'products-cid'+#p0+'-page-'+#p1+'-'+#p2")
    public Page4Navigator<Product> list(Integer cid, Integer start, Integer size, Integer navigatePages) {
        Category category = categoryDao.getOne(cid);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Product> pageFromJPA = productDao.findByCategory(category, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public void fill(Category category) {
        IProductService productService = SpringContextUtil.getBean(IProductService.class);
        List<Product> products = productService.listByCategory(category);
        productImageService.setFirstProductImages(products);
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += ConstantKey.PRODUCT_NUMBER_EACH_ROW) {
                int size = i + ConstantKey.PRODUCT_NUMBER_EACH_ROW;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    @Cacheable(key = "'products-cid-'+#p0.id")
    public List<Product> listByCategory(Category category) {
        return productDao.findByCategoryOrderById(category);
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        product.setSaleCount(orderItemService.getSaleCount(product));
        product.setReviewCount(reviewService.getCount(product));
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, Integer start, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        return productDao.findByNameLike("%" + keyword + "%", pageable);
    }

}
