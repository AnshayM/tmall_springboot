package pers.anshay.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import pers.anshay.tmall.util.PortUtil;

/**
 * 项目启动类
 *
 * @author: Anshay
 * @date: 2018/11/29
 */
@SpringBootApplication
@EnableCaching
public class Application {
    static {
        PortUtil.checkPort(6379, "Redis 服务端", true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
