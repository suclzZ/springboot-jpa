package com.sucl.jpa.core.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sucl
 * @date 2019/6/18
 */
@JobDesc(name = "测试任务")
@Component
public class SimpleJobProvider implements JobProvider{
    @Override
    public void runJob(JobExecutionContext context, String name) {
        System.out.println("调度任务测试..."+new Date());
    }
}
