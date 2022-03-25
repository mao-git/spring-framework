package com.devs4j.users.repository;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.devs4j.users.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	@Query("SELECT u.username FROM User u")
	public Page<String>findUsernames(PageRequest pageRequest);
}
