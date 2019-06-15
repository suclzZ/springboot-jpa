package com.sucl.jpa.core.config;

import com.sucl.jpa.core.method.support.ConditionHandlerMethodArgumentResolver;
import com.sucl.jpa.core.method.support.OrderHandlerMethodArgumentResolver;
import com.sucl.jpa.core.method.support.PagerHandlerMethodArgumentResolver;
import com.sucl.jpa.core.method.support.SortHandlerMethodArgumentResolver;
import com.sucl.jpa.core.orm.hibernate.HibernateAwareObjectMapper;
import com.sucl.jpa.core.service.impl.RefreshInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * WebMvcConfigurationSupport、WebMvcConfigurerAdapter
 * 因为WebMvcConfigurationSupport与WebMvcAutoConfiguration互斥，导致WebMvcAutoConfiguration中的配置会有很多失效，比如静态资源访问
 * 在处理jpa延迟加载时，如果使用的是WebMvcConfigurationSupport，那么原有的MappingJackson2HttpMessageConverter不会替换，
 * 此时需呀我们手动处理，可见configureMessageConverters
 * @author sucl
 * @date 2019/4/3
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 注册自定义参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PagerHandlerMethodArgumentResolver());
        argumentResolvers.add(new ConditionHandlerMethodArgumentResolver());
        argumentResolvers.add(new OrderHandlerMethodArgumentResolver());
        argumentResolvers.add(new SortHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 监听请求国际化 uri?lang=en_US
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    /**
     * 本地化配置
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    /**
     * 可刷新服务
     * @return
     */
    @Bean
    public RefreshInterceptor refreshInterceptor(){
        return new RefreshInterceptor();
    }

    /**
     * 注册过国际化请求参数拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(refreshInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 将返回值统一化处理，目前通过HandlerMethodReturnValueHandlerModifier实现，主要是修改ResponseBody的逻辑
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    /**
     * 静态资源请求处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
        super.addResourceHandlers(registry);
    }

    @Autowired(required = false)
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;
    /**
     * 修改默认的消息转换器
     * 处理jpa查询数据JavassistLazyInitializer 相关问题
     * 这样把默认的ObjectMapper修改了...
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for(HttpMessageConverter converter: converters){
            if(converter instanceof MappingJackson2HttpMessageConverter){
                HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
                if(jackson2ObjectMapperBuilder!=null){
                    jackson2ObjectMapperBuilder.configure(hibernateAwareObjectMapper);
                }
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(hibernateAwareObjectMapper);
            }
        }
        super.extendMessageConverters(converters);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }
/**
     * 无效
     * 按道理这样写会在Jackson2ObjectMapperBuilder中将该module注册到ObjectMapper中，然而都不起作用
     * @return
     */
//    @Bean
//    public Hibernate4Module hibernate4Module(){
//        Hibernate4Module hibernate4Module = new Hibernate4Module();
//        hibernate4Module.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION, false);
//        return hibernate4Module;
//    }

}
