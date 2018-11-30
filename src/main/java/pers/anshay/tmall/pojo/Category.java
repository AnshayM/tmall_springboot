package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 分类实体
 *
 * @author: Anshay
 * @date: 2018/11/28
 */
@Entity
@Table(name = "category")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Category {
    /**
     * 因为是做前后端分离，而前后端数据交互用的是 json 格式。 那么 Category 对象就会被转换为 json 数据。
     * 而本项目使用 jpa 来做实体类的持久化，jpa 默认会使用 hibernate,
     * 在 jpa 工作过程中，就会创造代理类来继承 Category ，
     * 并添加 handler 和 hibernateLazyInitializer 这两个无需要 json 化的属性，
     * 所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
