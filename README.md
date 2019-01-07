# tmall_springboot
基于springboot的仿天猫前后端分离项目。

这是为了整理最近学习的技术而做的一个练习项目。项目本身是前后端分离的，但是因为部分原因，前台部分使用html文件的方式放在resources文件下，后台没有特别处理。

前后台涉及的技术将在后面慢慢添加进来。

前端：Vue.js, bootstrap

后端：Springboot, mybatis, jpa，shiro,redis

 
 因为是做前后端分离，而前后端数据交互用的是 json 格式。 那么Pojo对象就会被转换为 json 数据。而本项目使用 jpa 来做实体类的持久化，
 jpa 默认会使用 hibernate,在 jpa 工作过程中，就会创造代理类来继承该Pojo类，并添加 handler 和 hibernateLazyInitializer 这两个无需要 json 化的属性，
 所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。
     
 调用Subject的login和logout方法来控制登录和登出动作
 [我的shiro学习记录](https://github.com/AnshayM/shiro/blob/master/README.md)
 
 使用redis时，增加，删除和修改用的注解都是:@CacheEvict(allEntries=true)，其意义是删除 categories~keys 里的所有的keys. 
 但并不使用	@CachePut(key="'category-one-'+ #p0")，后者作用是以 category-one-id 的方式增加到 Redis中去。 
 这样做本身其实是没有问题的，而且在 get 的时候，还可以使用，但是最后还是放弃这种做法了。
 因为，虽然这种方式可以在 redis 中增加一条数据，但是： 它并不能更新分页缓存 categories-page-0-5 里的数据， 这样会出现数据不一致的问题。
 
 后面补上基本的redis安装和启动说明，以供不了解redis的用户也能下载运行。


开发规范，每次提交时都先使用阿里巴巴编码规约插件扫描代码，更正后再提交。虽然是个人开发的，但是保持一个好的代码习惯也是必要的。

[项目中的图片资源链接](https://pan.baidu.com/s/1VtjKkjXAxAp54S0qZpTTuw) 
提取码：*o6k3*
