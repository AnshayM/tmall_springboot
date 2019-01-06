package pers.anshay.tmall.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 诱发部分方法走aop的工具类
 *
 * @author: Anshay
 * @date: 2019/1/6
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil() {

    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
