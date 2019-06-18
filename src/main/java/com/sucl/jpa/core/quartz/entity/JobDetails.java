package com.sucl.jpa.core.quartz.entity;

import com.sucl.jpa.core.orm.Domain;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Data
@Entity
@Table(name="qrtz_job_details")
@IdClass(JobDetailsKey.class)
public class JobDetails implements Domain {
    @Id
    private String schedName;

    @Id
    private String jobName;

    @Id
    private String jobGroup;

    @Column(name="DESCRIPTION")
    @Length(max=250)
    private String description;

    @Column(name="JOB_CLASS_NAME")
    @Length(max=250)
    private String jobClassName;
}
