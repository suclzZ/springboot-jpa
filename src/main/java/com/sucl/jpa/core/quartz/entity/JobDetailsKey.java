package com.sucl.jpa.core.quartz.entity;

import com.sucl.jpa.core.orm.Domain;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Data
public class JobDetailsKey implements Domain {

    @Id
    @Column(name="SCHED_NAME")
    @Length(max=120)
    private String schedName;

    @Id
    @Column(name="JOB_NAME")
    @Length(max=200)
    private String jobName;

    @Id
    @Column(name="JOB_GROUP")
    @Length(max=200)
    private String jobGroup;

}
