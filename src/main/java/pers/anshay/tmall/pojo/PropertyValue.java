package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 产品属性值
 *
 * @author: Anshay
 * @date: 2018/12/11
 */
@Entity
@Table(name = "propertyvalue")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class PropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Property property;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "id=" + id +
                ", product=" + product +
                ", property=" + property +
                ", value='" + value + '\'' +
                '}';
    }
}
