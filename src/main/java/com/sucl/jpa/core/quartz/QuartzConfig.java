package com.sucl.jpa.core.quartz;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author sucl
 * @date 2019/6/17
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        factory.setDataSource(dataSource);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

//    @Bean
//    public QuartzInitializerListener executorListener() {
//        return new QuartzInitializerListener();
//    }

    @Bean
    public JobDetailBean jobDetailBean(){
        JobDetailBean jobDetailBean = new JobDetailBean();
        jobDetailBean.setJobClass(DefaultQuartzJobBean.class);
        jobDetailBean.setDurability(true);
        return jobDetailBean;
    }

}
