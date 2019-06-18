package com.sucl.jpa.core.quartz.service.impl;

import com.sucl.jpa.core.quartz.dao.SchedulerJobDao;
import com.sucl.jpa.core.quartz.entity.SchedulerJob;
import com.sucl.jpa.core.quartz.service.SchedulerJobService;
import com.sucl.jpa.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Service
@Transactional
public class SchedulerJobServiceImpl extends BaseServiceImpl<SchedulerJobDao,SchedulerJob> implements SchedulerJobService {

    @Override
    public Class<SchedulerJob> getDomainClazz() {
        return SchedulerJob.class;
    }
}
