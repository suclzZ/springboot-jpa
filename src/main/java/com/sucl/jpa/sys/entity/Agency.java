package com.sucl.jpa.sys.entity;

import com.sucl.jpa.core.orm.Domain;
import com.sucl.jpa.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Data
@Entity
@Table(name = "sys_agency")
public class Agency implements Domain {

    @Id
    @Column(name = "agency_id",length = 36)
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String agencyId;

    @Column(name = "agency_code",length = 16)
    private String agencyCode;

    @Column(name = "agency_name",length = 56)
    private String agencyName;

    @Column(name = "duty",length = 24)
    private String duty;

    @Column(name = "parent_agency_id",length = 36)
    private String parentAgencyId;

    @Column(name = "description",length = 256)
    private String description;
}
