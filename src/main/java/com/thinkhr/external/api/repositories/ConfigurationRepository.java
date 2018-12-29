package com.thinkhr.external.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thinkhr.external.api.db.entities.Configuration;

/**
 * Configuration repository for configuration entity.
 *  
 * @author Surabhi Bhawsar
 * @since 2018-01-23 
 *
 */
public interface ConfigurationRepository
        extends PagingAndSortingRepository<Configuration, Integer>, JpaSpecificationExecutor<Configuration> {

    /**
     * Find first configuration by Configuration Id and company ID
     * 
     * @param configurationId
     * @param companyId
     * @return
     */
    public Configuration findFirstByConfigurationIdAndCompanyId(Integer configurationId, Integer companyId);


    /**
     * Find first configuration by Configuration Id and company ID
     * 
     * @param configuration
     * @param companyId
     * @return
     */
    public Configuration findFirstByConfigurationNameAndCompanyId(String configurationName, Integer companyId);

    /**
     * Soft Delete Configuration
     * 
     * @param configurationId
     */
    @Query("update Configuration c set deleted=UNIX_TIMESTAMP() where c.configurationId = :configurationId")
    @Modifying
    @Transactional
    public void softDelete(@Param(value = "configurationId")Integer configurationId);

    /**
     * Find first configuration by Company Id and Master Configuration flag
     * 
     * @param brokerId
     * @param i
     * @return 
     */
    public Configuration findFirstByCompanyIdAndMasterConfiguration(Integer brokerId,
            int isMaster);
    
    /**
     * Find all alternate Configurations for the broker
     * 
     * @param brokerId
     * @param skus
     * @return
     */
    public List<Configuration> findByCompanyIdAndMasterConfiguration(Integer brokerId,
            int i);

    /**
     * Find configurations by companyId
     * 
     * @param brokerId
     * @return
     */
    public List<Configuration> findByCompanyId(Integer companyId);
}