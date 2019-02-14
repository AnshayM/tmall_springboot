package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pers.anshay.tmall.util.ConstantKey;

import javax.persistence.*;

/**
 * 用户
 *
 * @author: Anshay
 * @date: 2018/12/15
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String name;
    private String password;
    private String salt;

    @Transient
    private String anonymousName;

    /**
     * 设置匿名
     *
     * @return
     */
    public String getAnonymousName() {
        if (null != anonymousName) {
            return anonymousName;
        } else if (null == name) {
            anonymousName = null;
        } else if (name.length() <= 1) {
            anonymousName = "*";
        } else if (name.length() == ConstantKey.ANONYMOUS_NAME_LENGTH_TWO) {
            anonymousName = name.substring(0, 1) + "*";
        } else {
            char[] chars = name.toCharArray();
            for (int i = 1; i < chars.length - 1; i++) {
                chars[i] = '*';
            }
            anonymousName = new String(chars);
        }
        return anonymousName;
    }

}
