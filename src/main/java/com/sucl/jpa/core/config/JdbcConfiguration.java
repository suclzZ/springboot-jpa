package com.sucl.jpa.core.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author sucl
 * @date 2019/4/18
 */
@Configuration
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class JdbcConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

//    @Primary
//    @Bean
//    public PlatformTransactionManager transactionManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Primary
//    @Bean
//    public TransactionDefinition transactionDefinition(){
//        return new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
//    }
}
