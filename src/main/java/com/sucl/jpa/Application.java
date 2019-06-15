package com.sucl.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author sucl
 * @date 2019/4/1
 */
@SpringBootApplication
//@EnableTransactionManagement
//@EnableJpaRepositories(value = {"com.sucl.**.dao"})
//@EntityScan
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication();

        application.run(Application.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return super.configure(builder);
        return builder.sources(Application.class);
    }

}
