package pers.anshay.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;
    private String password;
    private String salt;

    @Transient
    private String anonymousName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**设置匿名
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
        } else if (name.length() == 2) {
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

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }
}