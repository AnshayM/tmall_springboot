package pers.anshay.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 项目启动类
 *
 * @author: Anshay
 * @date: 2018/11/29
 */
@SpringBootApplication
@ServletComponentScan
//@EnableCaching
public class Application extends SpringBootServletInitializer {
    //    static {
    /*本地调试的时候，用来检测redis服务是否开启，部署时删除*/
//        PortUtil.checkPort(6379, "Redis 服务端", true);
//    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
