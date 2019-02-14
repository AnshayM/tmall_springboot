package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 评论
 *
 * @author: Anshay
 * @date: 2018/12/25
 */
@Entity
@Table(name = "review")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    private String content;
    private Date createDate;

}
