/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucl.jpa.core.config;

import com.sucl.jpa.core.service.FreemarkerVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Freemarker配置
 *
 * @author sucl
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(FreeMarkerProperties.class)
public class FreemarkerConfiguration {
    private FreeMarkerProperties properties;
    private Map<String, Object> variablesMap = new HashMap<>();

    public void setVariablesMap(List<FreemarkerVariable> variables) {
        if (!CollectionUtils.isEmpty(variables)) {
            for(FreemarkerVariable var : variables){
                if(!variablesMap.containsKey(var.getName()) && var.getVariables()!=null){
                    variablesMap.put(var.getName(),var.getVariables());
                }else{
                    log.warn("freemarkere variable :{} is existed!",var.getName());
                }
            }
        }
    }

    public FreemarkerConfiguration(FreeMarkerProperties freeMarkerProperties){
        this.properties = freeMarkerProperties;
    }

    @Bean
    @ConditionalOnBean(FreemarkerVariable.class)
    public FreemarkerVariablesHelper freemarkerVariablesHelper(List<FreemarkerVariable> variables){
       FreemarkerVariablesHelper freemarkerVariablesHelper = new FreemarkerVariablesHelper(variables);
       this.setVariablesMap(freemarkerVariablesHelper.getVariables());
       return freemarkerVariablesHelper;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
        configurer.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        configurer.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.putAll(this.properties.getSettings());
        settings.setProperty("default_encoding", "utf-8");
        configurer.setFreemarkerSettings(settings);

        configurer.setFreemarkerVariables(variablesMap);
        return configurer;
    }

}
