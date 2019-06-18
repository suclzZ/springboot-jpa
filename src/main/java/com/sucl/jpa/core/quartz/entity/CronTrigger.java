package com.sucl.jpa.core.quartz.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Data
@Entity
@Table(name="qrtz_cron_triggers")
@IdClass(SchedulerJobKey.class)
public class CronTrigger {

    @Id
    private String schedName;

    @Id
    private String triggerName;

    @Id
    private String triggerGroup;

    @Column(name="CRON_EXPRESSION")
    @Length(max=200)
    private String cronExpression;

    @Column(name="TIME_ZONE_ID")
    @Length(max=200)
    private String timeZoneId;
}
