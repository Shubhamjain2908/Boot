package com.thinkhr.external.api.config;

import static com.thinkhr.external.api.ApplicationConstants.PATTERN_V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.thinkhr.external.api.interceptors.APIProcessingTimeInterceptor;
import com.thinkhr.external.api.interceptors.APITrackingInterceptor;
import com.thinkhr.external.api.interceptors.JwtTokenInterceptor;
import com.thinkhr.external.api.services.AuthorizationManager;

/**
 * Application configuration class
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-14
 *
 */
@Configuration
@EnableWebMvc
@EnableAsync
public class AppConfig extends WebMvcConfigurerAdapter {
    
    @Value("${app.environment}")
    private String environment;

    @Autowired 
    AuthorizationManager authorizationManager; 
    
    @Value("${JWT.jwt_key}")
    private String key;

    @Value("${JWT.jwt_iss}")
    private String iss;
    
    /**
     * API Tracking Interceptor Bean for tracking JAPI Request Events
     * 
     * @return
     */
    @Bean
    public APITrackingInterceptor getApiTrackingInterceptor() {
        return new APITrackingInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new APIProcessingTimeInterceptor()).addPathPatterns(PATTERN_V1);
        registry.addInterceptor(new JwtTokenInterceptor(key, iss, authorizationManager, environment))
                .addPathPatterns(PATTERN_V1);
        registry.addInterceptor(getApiTrackingInterceptor()).addPathPatterns(PATTERN_V1);
    }
}