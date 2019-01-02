package pers.anshay.tmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pers.anshay.tmall.interceptor.LoginInterceptor;

/**
 * 拦截器配置类
 *
 * @author: Anshay
 * @date: 2019/1/2
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**");
    }
}
