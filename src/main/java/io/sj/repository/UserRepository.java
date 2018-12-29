package io.sj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.sj.entity.User;

/**
 * @author SHUBHAM JAIN
 */
public interface UserRepository extends JpaRepository<User, Long> {
}