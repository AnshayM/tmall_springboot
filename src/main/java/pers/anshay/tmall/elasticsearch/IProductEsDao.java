package pers.anshay.tmall.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pers.anshay.tmall.pojo.Product;

/**
 * 用于链接 ElasticSearch 的DAO
 * 因为jpa的dao做了redis的链接，如果放在同有个包下，会彼此影响，出现启动异常
 *
 * @author: Anshay
 * @date: 2019/1/16
 */
public interface IProductEsDao extends ElasticsearchRepository<Product, Integer> {
}
