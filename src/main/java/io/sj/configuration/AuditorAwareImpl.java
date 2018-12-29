package io.sj.configuration;


import org.springframework.data.domain.AuditorAware;

/**
 * @author SHUBHAM JAIN
 */
public class AuditorAwareImpl implements AuditorAware<String> {
	
    @Override
    public String getCurrentAuditor() {
    	System.out.println("AuditorAwareImpl method");
        return "Mr. Auditor";
    }

}