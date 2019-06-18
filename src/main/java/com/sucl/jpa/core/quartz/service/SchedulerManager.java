package com.sucl.jpa.core.quartz.service;

import com.sucl.jpa.core.orm.Condition;
import com.sucl.jpa.core.orm.Order;
import com.sucl.jpa.core.orm.ext.Pager;
import com.sucl.jpa.core.quartz.entity.SchedulerJob;
import com.sucl.jpa.core.quartz.entity.SchedulerJobKey;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/6/17
 */
public interface SchedulerManager {

    Pager getPagerSchedulerJobs(Pager pager, Collection<Condition> conditions, Collection<Order> orders);

    void pause(SchedulerJobKey schedulerJobKey);

    void resume(SchedulerJobKey schedulerJobKey);

    void remove(SchedulerJobKey schedulerJobKey);

    SchedulerJob getSchedulerJob(SchedulerJobKey schedulerJobKey);

    SchedulerJob saveSchedulerJob(SchedulerJob schedulerJobKey);

    List<SchedulerJob> getSchedulerJobsByProperty(String property, String value);

    List<Map<String, String>> getJobProviders();
}
