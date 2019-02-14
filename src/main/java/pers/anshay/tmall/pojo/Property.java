package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * 属性管理
 *
 * @author: Anshay
 * @date: 2018/12/4
 */
@Entity
@Table(name = "property")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;

}
