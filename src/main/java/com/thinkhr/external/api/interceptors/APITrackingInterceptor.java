package com.thinkhr.external.api.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.thinkhr.external.api.services.EventService;

/**
 * @author Surabhi Bhawsar
 * @Since 2018-03-05
 *
 */

public class APITrackingInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    EventService eventService;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        //TO track only for CUD APIs only, ignore GET APIs
        if (!RequestMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            eventService.trackRequest(request);
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
