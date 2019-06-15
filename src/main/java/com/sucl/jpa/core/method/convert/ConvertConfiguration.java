package com.sucl.jpa.core.method.convert;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

/**
 * @author sucl
 * @date 2019/5/29
 */
@Configuration
public class ConvertConfiguration {

//    @Bean(name="conversionService")
    public ConversionServiceFactoryBean conversionService(Set<Converter> converters){
        ConversionServiceFactoryBean conversionServiceFactoryBean=new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }
}
