package pers.anshay.tmall.service;

import org.springframework.web.multipart.MultipartFile;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.util.Page4Navigator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    /**
     * 添加
     *
     * @param category category
     */
    void add(Category category);

    /**
     * 添加或者修改分类图片
     *
     * @param category category
     * @param image    image
     * @param request  request
     * @throws IOException
     */
    void saveOrUpdateImageFile(Category category, MultipartFile image, HttpServletRequest request) throws IOException;

}
