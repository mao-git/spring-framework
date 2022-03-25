package com.devs4j.users.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.devs4j.users.entities.Role;
import com.devs4j.users.entities.User;
import com.devs4j.users.models.AuditDetails;
import com.devs4j.users.models.Devs4jSecurityRule;
import com.devs4j.users.repository.RoleRepository;
import com.devs4j.users.repository.UserInRoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(RoleService.class);

	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	public Role createRole(Role role) {
		Role roleCreated = roleRepository.save(role);
		
		AuditDetails auditDetails = new AuditDetails(SecurityContextHolder.getContext().getAuthentication().getName(),
				role.getName());
		try {
			kafkaTemplate.send("devs4j-topic", objectMapper.writeValueAsString(auditDetails));
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error parsing the message");
		}

		return roleCreated;
	}

	public Role updateRole(Integer roleId, Role role) {
		Optional<Role> result = roleRepository.findById(roleId);

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn't exits.", roleId));
		}

		return roleRepository.save(role);
	}

	public void deleteRole(Integer roleId) {
		Optional<Role> result = roleRepository.findById(roleId);

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d doesn't exits.", roleId));
		}

		roleRepository.delete(result.get());
	}

	@Devs4jSecurityRule
	public List<User> getUsersByRole(String roleName) {
		log.info("Getting roles by name {}", roleName);
		return userInRoleRepository.findUserByRoleName(roleName);
	}
}
