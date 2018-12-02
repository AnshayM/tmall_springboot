package pers.anshay.tmall.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理工具类
 *
 * @author: Anshay
 * @date: 2018/12/2
 */
public class ImageUtil {
    /**
     * 确保图片的二进制格式是jpg
     * 仅仅通过"ImageIO.write(img, "jpg", file);"不足以保证转换出来的jpg文件显示正常。
     * 这段转换代码，可以确保转换后jpg的图片显示正常，而不会出现暗红色( 有一定几率出现)。
     *
     * @param file file
     * @return img
     */
    public static BufferedImage change2jpg(File file) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] rgbMasks = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel rgbOpaque = new DirectColorModel(32, rgbMasks[0], rgbMasks[1], rgbMasks[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, rgbMasks, null);
            BufferedImage img = new BufferedImage(rgbOpaque, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 改变图片大小
     *
     * @param srcFile  srcFile
     * @param width    width
     * @param height   height
     * @param destFile destFile
     */
    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            Image img = ImageIO.read(srcFile);
            img = resizeImage(img, width, height);
            ImageIO.write((RenderedImage) img, "jpg", destFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 改变图片大小
     *
     * @param srcImage srcImage
     * @param width width
     * @param height height
     * @return buffImg
     */
    public static Image resizeImage(Image srcImage, int width, int height) {
        try {

            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
