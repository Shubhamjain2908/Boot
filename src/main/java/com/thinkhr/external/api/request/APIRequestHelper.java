package com.thinkhr.external.api.request;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.JsonParser;
import com.thinkhr.external.api.helpers.JAPIRequestWrapper;

/**
 * Helper class to deal with HttpRequest object for application
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-16
 *
 */
public class APIRequestHelper {

    /**
     * Fetch request object from RequestContextHolder 
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletReqAttr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if ( servletReqAttr != null) {
            return servletReqAttr.getRequest();
        }
        return null;
    }

    /**
     * To set request attributes 
     * 
     * @param attributeName
     * @param attributeValue
     */
    public static void setRequestAttribute(String attributeName, Object attributeValue) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(attributeName, attributeValue);
        }
    }

    /**
     * To fetch attribute value from request for given attribute name 
     * 
     * @param attributeName
     * @return
     */
    public static Object getRequestAttribute(String attributeName) {
        HttpServletRequest request = getRequest();
        Object attrVal = null; 
        if (request != null) {
            return request.getAttribute(attributeName);
        }
        return attrVal;
    }
    
    /**
     * Get JSON string for request
     * 
     * @param request
     * @return
     */
    public static String requestToJson(JAPIRequestWrapper request) throws IOException {
        if (RequestMethod.GET.name().equalsIgnoreCase(request.getMethod()) || RequestMethod.DELETE.name().equalsIgnoreCase(request.getMethod())) {
            return "";
        }
        String jb = new String();
        try( ServletInputStream inputStream = request.getInputStream();) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");
            jb = writer.toString();
            return new JsonParser().parse(jb.toString()).getAsJsonObject().toString();
        }
    }
    
    /**
     * Get Request Headers Map
     * 
     * @param request
     * @return
     */
    public static Map<String,String> getRequestHeaders() {
        
        HttpServletRequest request = getRequest();
        Map<String,String> headers = new HashMap<String, String>();
        Enumeration<String> headerNames =  request.getHeaderNames();
        Collections.list(headerNames).forEach(h -> headers.put(h, request.getHeader(h)));
        
        return headers;
    }


}