package com.sucl.jpa.core.service.impl;

import com.sucl.jpa.core.service.RefreshService;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**通过参数refresh控制是否调用InitService接口的实现bean
 * url?refresh=true
 * @author sucl
 * @date 2019/4/24
 */
@NoArgsConstructor
public class RefreshInterceptor implements HandlerInterceptor,ApplicationContextAware {
    private static final String DEFAULT_PARAM_REFRESH = "refresh";
    private List<RefreshService> refreshServices;
    private String param = DEFAULT_PARAM_REFRESH;

    public RefreshInterceptor(String param){
        this.param = param;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String refresh = WebUtils.findParameterValue(request, param);
        boolean doInterceptor = refresh!=null && Boolean.valueOf(refresh);
        if(refreshServices !=null && doInterceptor){
            for(RefreshService refreshService : refreshServices){
                refreshService.refresh();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RefreshService> initService = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RefreshService.class, true, false);
        if(initService!=null && initService.size()>0){
            this.refreshServices = new ArrayList<>(initService.values());
        }
    }
}
