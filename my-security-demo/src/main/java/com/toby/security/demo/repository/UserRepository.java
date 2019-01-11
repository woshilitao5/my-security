package com.toby.security.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toby.security.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
	@Query("select u from User u "
			+ "where u.userName = :userName")
	List<User> findByUserName(@Param("userName") String userName);

	User findByMobile(String mobile);
}