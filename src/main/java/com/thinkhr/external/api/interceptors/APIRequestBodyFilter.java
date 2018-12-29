package com.thinkhr.external.api.interceptors;


import static com.thinkhr.external.api.ApplicationConstants.REQUEST_BODY_JSON;
import static com.thinkhr.external.api.request.APIRequestHelper.requestToJson;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkhr.external.api.helpers.JAPIRequestWrapper;

/**
 * API Request Tracking Filter, to collect request body from a request object. 
 * 
 * @author Surabhi Bhawsar
 * @since 2018-03-05
 *
 */

@Component
public class APIRequestBodyFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest currentRequest = (HttpServletRequest) request;
        JAPIRequestWrapper wrappedRequest = new JAPIRequestWrapper(currentRequest);
        String requestJson;
        if(MediaType.APPLICATION_JSON_VALUE.equals(wrappedRequest.getContentType()) || RequestMethod.GET.name().equals(wrappedRequest.getMethod()) 
                || RequestMethod.DELETE.name().equals(wrappedRequest.getMethod())){
            requestJson = requestToJson(wrappedRequest);
        } else {
            requestJson = wrappedRequest.getBody();
        }
        wrappedRequest.setAttribute(REQUEST_BODY_JSON, requestJson);
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
        //Not required
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Not required
    }

}
