package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.ApplicationConstants.COMPANY_TYPE_BROKER;
import static com.thinkhr.external.api.ApplicationConstants.WELCOME_EMAIL_TYPE;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.db.entities.Configuration;
import com.thinkhr.external.api.db.entities.EmailTemplate;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.exception.ApplicationException;


/**
 *
 * Provides a collection of all services related with Company with type = Broker
 * database object

 * @author Surabhi Bhawsar
 * @Since 2018-01-23
 *
 * 
 */
@Service
public class BrokerService extends CompanyService {
    
    @Value("${com.thinkhr.external.api.broker.welcome.emailTemplateid}")
    private String defaultWelcomeEmailTemplateId;
    
    /**
    *
    * To fetch companies records. Based on given parameters companies records will be filtered out.
    * 
    * @param Integer offset First record index from database after sorting. Default value is 0
    * @param Integer limit Number of records to be fetched. Default value is 50
    * @param String sortField Field on which records needs to be sorted
    * @param String searchSpec Search string for filtering results
    * @param Map<String, String>
    * @return List<Company> object 
    * @throws ApplicationException 
    * 
    */
   public List<Company> getAllBroker(Integer offset, 
           Integer limit,
           String sortField, 
           String searchSpec, 
           Map<String, String> requestParameters) throws ApplicationException {

        if (requestParameters == null) {
            requestParameters = new HashMap<String, String>();
        }
        requestParameters.put("companyType", COMPANY_TYPE_BROKER); //To always filter broker records;
        return getAllCompany(offset, limit, sortField, searchSpec, requestParameters);
   }

   /**
    * Delete specific broker from database
    * 
    * @param brokerId
    */
   public int deleteBroker(int brokerId) throws ApplicationException {
       Company company = companyRepository.findOne(brokerId);

       if (null == company) {
           throw ApplicationException.createEntityNotFoundError(APIErrorCodes.ENTITY_NOT_FOUND, "broker", "brokerId="+brokerId);
       }

       return deleteCompany(brokerId, company);
   }


    /**
     * Add a company in database with type = broker_partner
     * 
     * @param company object
     */
    @Transactional
    public Company addBroker(Company company) {
        
        if (StringUtils.isEmpty(company.getCompanyType())) {
            company.setCompanyType(COMPANY_TYPE_BROKER);
        }
        
        company = addCompany(company, null);
        
        //Create Master configuration with new company
        Configuration configuration = configurationRepository.save(createMasterConfiguration(company.getCompanyId()));
        company.setBroker(company.getCompanyId());
        company.setConfigurationId(configuration.getConfigurationId());
        
        company = companyRepository.save(company); //To update with brokerId and master configuration id
        learnCompanyService.updateLearnCompany(company);
        
        createDefaultWelcomeEmailTemplate(company);
        
        return company;
    }

    /**
     * Create Default Email Template for a new broker
     * 
     * @param company
     */
    private void createDefaultWelcomeEmailTemplate(Company company) {
        
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setBrokerId(company.getCompanyId());
        emailTemplate.setType(WELCOME_EMAIL_TYPE);
        emailTemplate.setSendgridTemplateId(defaultWelcomeEmailTemplateId);
        
        emailTemplateRepository.save(emailTemplate);
        
    }

    /**
     * Fetch specific company from database
     * 
     * @param brokerId
     * @return Company object 
     */
    public Company getBroker(Integer brokerId) {
        Company company =  companyRepository.findOne(brokerId);

        if (null == company) {
            throw ApplicationException.createEntityNotFoundError(APIErrorCodes.ENTITY_NOT_FOUND,
                    "broker", "brokerId="+ brokerId);
        }

        return company;
    }

    /**
     * Update a company in database
     * 
     * @param brokerId
     * @param companyJson
     * @return
     * @throws ApplicationException
     * @throws IOException 
     * @throws JsonProcessingException 
     */
    @Transactional
    public Company updateBroker(Integer brokerId, String companyJson) 
            throws ApplicationException, JsonProcessingException, IOException {

        Company companyInDb = companyRepository.findOne(brokerId);
        if (null == companyInDb) {
            throw ApplicationException.createEntityNotFoundError(APIErrorCodes.ENTITY_NOT_FOUND, "broker", "brokerId="+brokerId);
        }
        
        return updateCompany(companyJson, brokerId, companyInDb);
    }

    
    /**
     * Validate and get broker for given brokerId
     * 
     * @param brokerId
     * @return
     */
    @Override
    public Company validateBrokerId(Integer brokerId) {
        if (brokerId == null) { //For broker creation, initially brokerId will be null
            return null;
        }
        
        return super.validateBrokerId(brokerId);
    }

    /**
     * To validate duplicate company record for client_type
     */
    @Override
    public boolean isDuplicateCompany(Company company) {
        //find matching company by given company name and company type
        Company companyFromDB = companyRepository.findFirstByCompanyNameAndCompanyType(company.getCompanyName(),
                company.getCompanyType());

        return companyFromDB == null ? false : true;
    }
    
}