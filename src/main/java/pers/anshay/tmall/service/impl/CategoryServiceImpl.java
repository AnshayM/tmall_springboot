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
import org.springframework.web.multipart.MultipartFile;
import pers.anshay.tmall.dao.CategoryDao;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.util.ImageUtil;
import pers.anshay.tmall.util.Page4Navigator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: Anshay
 * @date: 2018/11/28
 */
@Service
@CacheConfig(cacheNames = "categories")
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Override
    @Cacheable(key = "'categories-all'")
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDao.findAll(sort);
    }

    @Override
    @Cacheable(key = "'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(Integer start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = categoryDao.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void add(Category category) {
        categoryDao.save(category);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        categoryDao.delete(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update(Category category) {
        categoryDao.save(category);
    }

    @Override
    @Cacheable(key = "'category-one-'+#p0")
    public Category get(Integer id) {
        return categoryDao.findOne(id);
    }

    @Override
    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    /**
     * 这个方法用于删除Product对象上的分类属性，在这里用不到redis
     *
     * @param categories categories
     */
    @Override
    public void removeCategoryFromProduct(List<Category> categories) {
        for (Category category : categories) {
            removeCategoryFromProduct(category);
        }
    }

    @Override
    public void removeCategoryFromProduct(Category category) {
        List<Product> products = category.getProducts();
        if (null != products) {
            for (Product product : products) {
                product.setCategory(null);
            }
        }
        List<List<Product>> productsByRow = category.getProductsByRow();
        if (null != productsByRow) {
            for (Product product : products) {
                product.setCategory(null);
            }
        }
    }

}
