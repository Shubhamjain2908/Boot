package io.sj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author SHUBHAM JAIN
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
    	System.out.println("AuditConfiguration");
        return new AuditorAwareImpl();
    }

}