package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * 订单项
 *
 * @author: Anshay
 * @date: 2018/12/17
 */
@Entity
@Table(name = "orderItem")
@JsonIgnoreProperties({"handler", "hibernateLayInitializer"})
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "oid")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    private int number;
}
