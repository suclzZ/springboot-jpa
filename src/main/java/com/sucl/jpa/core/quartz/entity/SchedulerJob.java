package com.sucl.jpa.core.quartz.entity;

import com.sucl.jpa.core.orm.Domain;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Data
@Entity
@Table(name="qrtz_triggers")
@IdClass(SchedulerJobKey.class)
public class SchedulerJob implements Domain {
    @Id
    private String schedName;

    @Id
    private String triggerName;

    @Id
    private String triggerGroup;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({@JoinColumn(name="SCHED_NAME",referencedColumnName="SCHED_NAME",insertable = false,updatable = false),
            @JoinColumn(name="JOB_NAME",referencedColumnName="JOB_NAME",insertable = false,updatable = false),
            @JoinColumn(name="JOB_GROUP",referencedColumnName="JOB_GROUP",insertable = false,updatable = false)})
    @NotFound(action = NotFoundAction.IGNORE)
    private JobDetails jobDetails;

    @ManyToOne
    @JoinColumns({@JoinColumn(name="SCHED_NAME", referencedColumnName="SCHED_NAME", insertable=false, updatable=false),
            @JoinColumn(name="TRIGGER_NAME", referencedColumnName="TRIGGER_NAME", insertable=false, updatable=false),
            @JoinColumn(name="TRIGGER_GROUP", referencedColumnName="TRIGGER_GROUP", insertable=false, updatable=false)})
    private CronTrigger cronTrigger;

    @Column(name="DESCRIPTION")
    @Length(max=200)
    private String description;

    @Column(name="trigger_State")
    @Length(max=200)
    private String triggerState;

    @Column(name="START_TIME")
    @Length(max=13)
    private String startTime;

    @Column(name="END_TIME")
    @Length(max=13)
    private String endTime;

    @Column(name="PREV_FIRE_TIME")
    @Length(max=13)
    private String prevFireTime;

    @Column(name="NEXT_FIRE_TIME")
    @Length(max=13)
    private String nextFireTime;

    @Column(name="CALENDAR_NAME")
    @Length(max=200)
    private String calendarName;

    @Column(name="PRIORITY")
    private int priority;

    @Column(name="MISFIRE_INSTR")
    private int misfireInsert;
}
