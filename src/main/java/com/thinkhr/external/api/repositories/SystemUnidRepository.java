package com.thinkhr.external.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.thinkhr.external.api.db.entities.SystemUnid;

/**
 * Email Template repository for Event entity.
 * 
 * @author Surabhi Bhawsar
 * @since 2018-01-03
 *
 */
public interface SystemUnidRepository extends CrudRepository<SystemUnid, Integer> {

}
