package pers.anshay.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pers.anshay.tmall.util.PortUtil;

/**
 * 项目启动类
 * ServletComponentScan注解、继承父类SpringBootServletInitializer、覆写方法configure用于打war包；
 * EnableCaching注解用于redis；
 * EnableElasticsearchRepositories和EnableJpaRepositories注解分别用于指定jpa和es对应的包，
 *
 * @author: Anshay
 * @date: 2018/11/29
 */
@ServletComponentScan
@EnableCaching
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "pers.anshay.tmall.elasticsearch")
@EnableJpaRepositories(basePackages = {"pers.anshay.tmall.dao", "pers.anshay.tmall.pojo"})
public class Application extends SpringBootServletInitializer {
    /*本地调试的时候，可用来检测相应服务是否开启，部署时注释*/
    /*static {
        PortUtil.checkPort(6379, "Redis 服务端", true);
        PortUtil.checkPort(9300, "ElasticSearch服务端", true);
        PortUtil.checkPort(5601, "Kibana工具", true);
    }*/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
