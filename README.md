
- [基于springboot的仿天猫前后端分离项目](#tmall_springboot)
- [技术选型](#技术选型)
- [前后端分离](##前后端分离)

# tmall_springboot
这是为了整理最近学习的技术而做的一个练习项目。项目本身是前后端分离的，但是因为部分原因，前台部分使用html文件的方式放在resources文件下，后台没有特别处理。

# 技术选型
- 前端：thymeleaf, Vue.js, bootstrap,

- 后端：Springboot, mybatis, jpa，shiro, redis, ElasticSearch

 ## 前后端分离
 这里没有使用Webstorm，而是偷了一些懒，将html、css、js文件还有图片文件都放在webapp下。  
 这里前后端数据交互采用json格式，Pojo对象会被转换为 json 数据。
 而本项目使用 **jpa** 来做实体类的持久化。jpa在工作过程中，会创造代理类来继承Pojo类，并添加 handler 和 hibernateLazyInitializer 这两个无需要 json 化的属性，
 所以在项目中使用JsonIgnoreProperties注解 把pojo类中的这两个属性忽略掉。
 
 ## shiro
 项目使用shiro控制权限，目前只使用shiro来检查用户的登录状态来引导界面。通过调用Subject的login和logout方法来控制。详情可参考 [我的shiro学习记录](https://github.com/AnshayM/shiro/blob/master/README.md)。  
 其他精细化的权限控制——例如不同角色拥有不同权限，还有待开发。
 
 ## redis
 在service层中使用redis时，增加，删除和修改用的注解都是:@CacheEvict(allEntries=true)，其意义是删除 categories~keys 里的所有的keys。
 本来是想使用	@CachePut(key="'category-one-'+ #p0")，这个作用是以 category-one-id 为key增加到 Redis中去。
 虽然这种方式可以在 redis 中增加一条数据，但是： 它并不能更新分页缓存 categories-page-0-5 里的数据，这样会出现数据不一致的问题，所以放弃了。
 
 如果在service的方法里调用另一个缓存管理的方法，不能够直接调用，需要通过一个工具再拿一次Service再调用，如下代码
```java
public void fill(Category category) {
	ProductService productService = SpringContextUtil.getBean(ProductService.class);
	List<Product> products = productService.listByCategory(category);
	productImageService.setFirstProdutImages(products);
	category.setProducts(products);
}
```
这里 listByCategory 方法本来就是 ProductService 的方法，却不能直接调用。 
因为 springboot 的缓存机制是通过切面编程 aop来实现的， 
从fill方法里直接调用 listByCategory 方法， aop 是拦截不到，也就不会走缓存。
通过这种 绕一绕 的方式故意诱发 aop, 这样才会按照期望的方式走redis缓存。  

- 关于Redis的了解和使用可以参考这里：  
[初步认识Redis](https://blog.csdn.net/qq_27665897/article/details/82149195)  
[Linux下Redis的安装、运行和远程连接](https://blog.csdn.net/qq_27665897/article/details/86441293) 

# 编码规范
  采用RestFull风格，并且每次提交时都先使用阿里巴巴编码规约插件扫描代码，修缮后再提交。
  虽然不是企业向项目，但保持一个好的代码习惯也是必要的。

# lombok
lombok是一个工具类，可以简化pojo类的编写。
在pojo类上加@Date注解，即可自动注入setter/getter方法、toString方法、各种构造器、实现链式调用等。

在idea中使用Lombok，需要安装相应插件。
![安装插件](https://i.loli.net/2019/02/12/5c62ea78b8382.png)

然后在设置中启动annotation processors。
![设置启动](https://i.loli.net/2019/02/12/5c62eac94c8a5.png)


更多使用信息可以查看[Lombok官方文档](https://projectlombok.org/features/all)。
# 图片资源
- 项目使用的图片资源有200多m，放在这里：  
[项目中的图片资源链接](https://pan.baidu.com/s/1VtjKkjXAxAp54S0qZpTTuw)—
提取码：*o6k3*
