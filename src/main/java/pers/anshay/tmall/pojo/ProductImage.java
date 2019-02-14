package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * ProductImage
 *
 * @author: Anshay
 * @date: 2018/12/8
 */
@Entity
@Table(name = "productimage")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "pid")
    @JsonBackReference
    private Product product;

    private String type;

}
