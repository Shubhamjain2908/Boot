package com.thinkhr.external.api.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.thinkhr.external.api.db.entities.Location;


/**
 * Company repository for company entity.
 *  
 * @author Surabhi Bhawsar
 * @since   2017-11-01 
 *
 */

public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> ,JpaSpecificationExecutor<Location> {

    @Query(value = "select * from locations where Client_ID = :companyId limit 1", nativeQuery = true)
    public Location findFirstByCompanyIdNonPrimary(@Param("companyId") Integer companyId);

}