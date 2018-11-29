package pers.anshay.tmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类,用于允许所有的请求都跨域。
 * 因为是第二次请求，第一次是获取html页面，第二次通过html页面上的js代码异步获取数据。
 * 一旦部署到服务器就容易面临跨域请求问题，这里允许所有访问都跨域，就不会通过ajax获取数据不到的问题。
 *
 * @auth: machao
 * @date: 2018/11/28
 */
@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
