package com.sucl.jpa.sys.entity;

import com.sucl.jpa.core.orm.Domain;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/8
 */
@Data
@Entity
@Table(name = "sys_role")
public class Role implements Domain {

    @Id
    @Column(name = "role_code",length = 24)
    private String roleCode;

    @Column(name = "role_name",length = 56)
    private String roleName;

    @Column(name = "description",length = 256)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "sys_role_menu",
            joinColumns = @JoinColumn(name = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "menu_code"))
    private List<Menu> menus;

    @Override
    public String toString() {
        return "Role{" +
                "roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
