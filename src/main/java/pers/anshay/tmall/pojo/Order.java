package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pers.anshay.tmall.util.ConstantKey;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @author: Anshay
 * @date: 2018/12/18
 */
@Entity
@Table(name = "order_")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    private String userMessage;
    private Date createDate;
    private Date payDate;
    private Date deliveryDate;
    private Date confirmDate;
    private String status;

    @Transient
    private List<OrderItem> orderItems;
    @Transient
    private float total;
    @Transient
    private int totalNumber;
    @Transient
    private String statusDesc;

    public String getStatusDesc() {
        if (null != statusDesc) {
            return statusDesc;
        }
        String desc = "未知";
        switch (status) {
            case ConstantKey.WAIT_PAY:
                desc = "待付";
                break;
            case ConstantKey.WAIT_DELIVERY:
                desc = "待发";
                break;
            case ConstantKey.WAIT_CONFIRM:
                desc = "待收";
                break;
            case ConstantKey.WAIT_REVIEW:
                desc = "等评";
                break;
            case ConstantKey.FINISH:
                desc = "完成";
                break;
            case ConstantKey.DELETE:
                desc = "刪除";
                break;
            default:
        }
        statusDesc = desc;
        return statusDesc;
    }

}
