package com.sucl.jpa.core.quartz.entity;

import com.sucl.jpa.core.orm.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerJobKey implements Domain {
    @Id
    @Column(name="SCHED_NAME")
    @Length(max=120)
    private String schedName;

    @Id
    @Column(name="TRIGGER_NAME")
    @Length(max=200)
    private String triggerName;

    @Id
    @Column(name="TRIGGER_GROUP")
    @Length(max=200)
    private String triggerGroup;
}
