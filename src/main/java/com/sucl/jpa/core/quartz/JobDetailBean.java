package com.sucl.jpa.core.quartz;

import org.quartz.Job;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.DelegatingJob;

import java.util.Map;

/**
 * @author sucl
 * @date 2019/6/17
 */
public class JobDetailBean extends JobDetailImpl implements BeanNameAware, ApplicationContextAware, InitializingBean {

    private Class<?> actualJobClass;
    private String beanName;
    private ApplicationContext applicationContext;
    private String applicationContextJobDataKey;

    public void setJobClass(Class jobClass) {
        if ((jobClass != null) && (!Job.class.isAssignableFrom(jobClass))) {
            super.setJobClass(DelegatingJob.class);
            this.actualJobClass = jobClass;
        } else {
            super.setJobClass(jobClass);
        }
    }

    public Class getJobClass() {
        return this.actualJobClass != null ? this.actualJobClass : super.getJobClass();
    }

    public void setJobDataAsMap(Map<String, ?> jobDataAsMap) {
        getJobDataMap().putAll(jobDataAsMap);
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setApplicationContextJobDataKey(String applicationContextJobDataKey) {
        this.applicationContextJobDataKey = applicationContextJobDataKey;
    }

    public void afterPropertiesSet() {
        if (getName() == null) {
            setName(this.beanName);
        }
        if (getGroup() == null) {
            setGroup("DEFAULT");
        }
        if (this.applicationContextJobDataKey != null) {
            if (this.applicationContext == null) {
                throw new IllegalStateException("JobDetailBean needs to be set up in an ApplicationContext to be able to handle an 'applicationContextJobDataKey'");
            }
            getJobDataMap().put(this.applicationContextJobDataKey, this.applicationContext);
        }
    }
}