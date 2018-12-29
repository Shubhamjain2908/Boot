package com.thinkhr.external.api.services.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thinkhr.external.api.ApplicationConstants;

/**
 * To keep some common util methods 
 * 
 * @author Ajay
 * @since 2017-11-22
 *
 */
public class CommonUtil {

    /**
     * This will return current date and time in UTC
     * 
     * @return
     */
    public static String getTodayInUTC() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(ApplicationConstants.VALID_FORMAT_YYYY_MM_DD);
        ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        return format.format(utcDateTime);
    }

    /**
     * Get tempID column value for Company entity.
     * 
     * @return
     */
    public static String getTempId() {
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTime().getTime();
        return String.valueOf(currentTime);
    }

    /**
     * 
     * @return
     */
    public static Long getNowInMiliseconds() {
        Date now = new Date();
        return now.getTime();
    }
    	
    
    /**
     * Generates and returns a hash.
     * 
     * @param value
     * @return
     */
    public static String generateHashedValue(Integer value) {
        long microTime = System.currentTimeMillis();
        String companyIdWithMicroTime = String.valueOf(microTime + value).replace(" ", "");
        String encodedString = Base64.getEncoder().encodeToString(companyIdWithMicroTime.getBytes());
        String reversedString = StringUtils.reverse(encodedString.replace("=", ""));
        return reversedString.toUpperCase();
    }

    /**
     * This will return current date in UTC
     * TODO: Need to implement so that returned date instance is for UTC time
     * @return
     */
    public static Date getCurrentDateInUTC() {
        Date dateInUTC = new Date();
        return dateInUTC;
    }

    /**
     * This function returns the first object which is instance of class cls from array of objects objs.
     * If no objects from objs is instance of cls then returns null.
     * 
     * @param cls
     * @param objs
     * @return
     */
    public static Object getObjectForClass(Class<?> cls, Object... objs) {
        for (Object o : objs) {
            if (cls.isInstance(o)) {
                return o;
            }
        }
        return null;
    }
    
    //TODO: Below code to be used in future for Email Template creation. Commenting the code as it has no use now.
    
    /**
     * Adds email template and configuration for new brokers
     * 
     * @param company
     * @param welcomeSenderEmail 
     * @param welcomeSenderEmailSubject 
     *//*
    private void createEmailTemplateAndConfiguration(Company company) {

        //Set the email template
        EmailTemplate emailTemplate = createEmailTemplate(company);
        
        //Create the Email Configurations
        List<EmailConfiguration> emailConfigurations = createEmailConfigurationWithTemplate(company, emailTemplate);
        
        emailTemplate.setEmailConfigurations(emailConfigurations);
        
        emailTemplateRepository.save(emailTemplate);
        
    }

    
    *//**
     * Creates an Email template for the new broker company
     * 
     * @param company
     * @return
     *//*
    private EmailTemplate createEmailTemplate(Company company) {

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setBrokerId(company.getCompanyId());
        emailTemplate.setType(WELCOME_EMAIL_TYPE);
        emailTemplate.setSendgridTemplateId(authTemplateId);
        
        return emailTemplate;
    }
    
    *//**
     * Creates email configuration for broker with the specified template
     * 
     * @param company
     * @param emailTemplate 
     * @return 
     *//*
    private List<EmailConfiguration> createEmailConfigurationWithTemplate(Company company,
            EmailTemplate emailTemplate) {

        List<EmailConfiguration> emailConfigurations = new ArrayList<EmailConfiguration>();
        
        //Set Email Configuration for Email Subject 
        EmailConfiguration emailConfigurationForSubject = new EmailConfiguration();
        EmailField emailFieldForSubject = new EmailField();
        emailFieldForSubject.setId(welcomeEmailSubjectId);
        emailConfigurationForSubject.setEmailField(emailFieldForSubject);
        emailConfigurationForSubject.setValue(company.getWelcomeSenderEmailSubject());
        emailConfigurationForSubject.setEmailTemplate(emailTemplate);
        emailConfigurations.add(emailConfigurationForSubject);
        
        //Set Email Configuration for Email
        EmailConfiguration emailConfigurationForMail = new EmailConfiguration();
        EmailField emailFieldForMail = new EmailField();
        emailFieldForMail.setId(welcomeEmailId);
        emailConfigurationForMail.setEmailField(emailFieldForMail);
        emailConfigurationForMail.setValue(company.getWelcomeSenderEmail());
        emailConfigurationForMail.setEmailTemplate(emailTemplate);
        emailConfigurations.add(emailConfigurationForMail);
        
        return emailConfigurations;
        
    }*/
	
}
