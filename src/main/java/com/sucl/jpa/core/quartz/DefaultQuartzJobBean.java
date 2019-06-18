package com.sucl.jpa.core.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;

/**
 * @author sucl
 * @date 2019/6/17
 */
public class DefaultQuartzJobBean extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object applicationContext = null;
        try {
            applicationContext = jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            this.logger.error(e.getMessage());
        }
        ApplicationContext context = null;
        if ((applicationContext instanceof ApplicationContext)) {
            context = (ApplicationContext) applicationContext;
        }

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        String groupName = jobKey.getGroup();
        String jobName = jobKey.getName();

        Object bean = null;
        try {
            bean = context.getBean(groupName);
        } catch (BeansException e) {
            this.logger.warn(e.getMessage());
            return;
        }

        if ((bean instanceof JobProvider)) {
            ((JobProvider) bean).runJob(jobExecutionContext, jobName);
            return;
        }

        ArgumentConvertingMethodInvoker invoker = new ArgumentConvertingMethodInvoker();
        Object[] arguments = {jobExecutionContext};
        invoker.setArguments(arguments);
        invoker.setTargetObject(bean);
        invoker.setTargetMethod(jobName);
        try {
            invoker.prepare();

            invoker.invoke();
            this.logger.info("调度：" + jobExecutionContext);
        } catch (InvocationTargetException e) {
            this.logger.info("" + e.getMessage());
        } catch (IllegalAccessException e) {
            this.logger.info("" + e.getMessage());
        } catch (ClassNotFoundException e) {
            this.logger.info("" + e.getMessage());
        } catch (NoSuchMethodException e) {
            this.logger.info(jobName + ":" + e.getMessage());
        }
    }
}
