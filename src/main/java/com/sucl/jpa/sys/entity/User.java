package com.sucl.jpa.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sucl.jpa.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Temporal
 * @Transient
 *
 * @author sucl
 * @date 2019/4/1
 */
@Data
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"password"})
public class User implements Domain {

    @Id
    @Column(name = "user_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency")
    private Agency agency;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_code"))
    private List<Role> roles;

    @Column(name = "username",length = 24)
    private String username;

    @Column(name = "password",length = 56)
    private String password;

    @Column(name = "user_caption",length = 56)
    private String userCaption;

    @Column(name = "sex",length = 2)
    private String sex;

    @Column(name = "age",length = 3)
    private String age;

    @Column(name = "birthday",length = 20)
    private String birthday;

    @Column(name = "telephone",length = 16)
    private String telephone;

    @Column(name = "email",length = 56)
    private String email;

    @Column(name = "address",length = 128)
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "description",length = 256)
    private String description;

    @Override
    public String toString() {
        return this.userCaption;
    }
}
