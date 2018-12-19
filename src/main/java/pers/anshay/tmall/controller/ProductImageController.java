package pers.anshay.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.anshay.tmall.pojo.Product;
import pers.anshay.tmall.pojo.ProductImage;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.service.IProductImageService;
import pers.anshay.tmall.service.IProductService;
import pers.anshay.tmall.util.ConstantKey;
import pers.anshay.tmall.util.ImageUtil;
import pers.anshay.tmall.util.TmResult;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * ProductImageController
 *
 * @author: Anshay
 * @date: 2018/12/8
 */
@RestController
@RequestMapping("/productImage")
public class ProductImageController {
    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IProductImageService productImageService;

    /**
     * 根据产品查询产品图片
     *
     * @param pid  productId
     * @param type single/detail
     * @return TmResult
     */
    @GetMapping("/{pid}/list")
    public TmResult list(@PathVariable("pid") Integer pid, String type) {
        Product product = productService.get(pid);
        List<ProductImage> list = productImageService.listProductImage(product, type);
        return new TmResult(true, "查询list成功", list);
    }

    /**
     * 添加
     *
     * @param pid     productId
     * @param type    single/detail
     * @param image   image
     * @param request request
     * @return TmResult
     */
    @PostMapping("/add")
    public TmResult add(Integer pid, String type, MultipartFile image, HttpServletRequest request) {
        ProductImage productImage = new ProductImage();
        Product product = productService.get(pid);
        productImage.setProduct(product);
        productImage.setType(type);

        productImageService.add(productImage);

        String folder = "img/";
        if (ConstantKey.TYPE_SINGLE.equals(type)) {
            folder += "productSingle";
        } else if (ConstantKey.TYPE_DETAIL.equals(type)) {
            folder += "productDetail";
        } else {
            return null;
        }

        File imgFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imgFolder, productImage.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ConstantKey.TYPE_SINGLE.equals(type)) {
            String imageFolderSmall = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolderMiddle = request.getServletContext().getRealPath("img/productSingle_middle");
            File folderSmall = new File(imageFolderSmall, fileName);
            File folderMiddle = new File(imageFolderMiddle, fileName);
            folderSmall.getParentFile().mkdirs();
            folderMiddle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, folderSmall);
            ImageUtil.resizeImage(file, 217, 190, folderMiddle);
        }

        return new TmResult(true, "添加成功", productImage);
    }

    /**
     * 删除
     *
     * @param id      id
     * @param request request
     * @return TmResult
     */
    @DeleteMapping("/{id}")
    public TmResult delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        ProductImage productImage = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        if (ConstantKey.TYPE_SINGLE.equals(productImage.getType())) {
            folder += "productSingle";
        } else if (ConstantKey.TYPE_DETAIL.equals(productImage.getType())) {
            folder += "productDetail";
        } else {
            return null;
        }
        File imgFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imgFolder, productImage.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if (ConstantKey.TYPE_SINGLE.equals(productImage.getType())) {
            String imageFolderSmall = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolderMiddle = request.getServletContext().getRealPath("img/productSingle_middle");
            File folderSmall = new File(imageFolderSmall, fileName);
            File folderMiddle = new File(imageFolderMiddle, fileName);
            folderSmall.delete();
            folderMiddle.delete();
        }
        return new TmResult(true, "删除成功");
    }
}
