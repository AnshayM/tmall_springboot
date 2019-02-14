package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
@Data
public class PropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ptid")
    private Property property;

    private String value;

}
