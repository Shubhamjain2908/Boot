package io.sj.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import lombok.AllArgsConstructor;

/**
 * This layer is used to intercept requests coming from client and 
 * send them to the server if they are valid. 
 * 
 * @author Shubham 
 * @since 12-03-2018
 *
 */
@AllArgsConstructor
public class ApiInterceptor extends HandlerInterceptorAdapter 
{	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		pathVariables.forEach((key, value) -> {
		    System.out.println("Key : " + key + " Value : " + value);
		});
		System.out.println(request.getUserPrincipal()+ " : "+request.getContextPath() +" : "+request.getPathInfo()+" : "+request.getRemoteAddr()+" : "+request.getRemoteHost()+" : "+request.getRemotePort()+" : "+request.getRemoteUser());
		if (request.getMethod().equals(RequestMethod.GET.name())) 
		{
			System.out.println(request.getUserPrincipal()+ " : "+request.getContextPath() +" : "+request.getPathInfo()+" : "+request.getRemoteAddr()+" : "+request.getRemoteHost()+" : "+request.getRemotePort()+" : "+request.getRemoteUser());
			
			return true;
		}
		return false;
	}

}