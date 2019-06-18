package com.sucl.jpa.core.quartz.service;

import com.sucl.jpa.core.quartz.dao.SchedulerJobDao;
import com.sucl.jpa.core.quartz.entity.SchedulerJob;
import com.sucl.jpa.core.service.BaseService;

/**
 * @author sucl
 * @date 2019/6/17
 */
public interface SchedulerJobService extends BaseService<SchedulerJobDao,SchedulerJob> {
}
