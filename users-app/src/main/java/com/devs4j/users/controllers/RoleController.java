package com.devs4j.users.controllers;

import java.util.List;
import com.devs4j.users.entities.Role;
import com.devs4j.users.entities.User;
import com.devs4j.users.services.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	@GetMapping
	public ResponseEntity<List<Role>> getRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name {}", authentication.getName());
		log.info("Principal {}", authentication.getPrincipal());
		log.info("Credentials {}", authentication.getCredentials());
		log.info("Roles {}", authentication.getAuthorities().toString());
		return new ResponseEntity<List<Role>>(roleService.getRoles(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Role> createRoles(@RequestBody Role role) {
		return new ResponseEntity<Role>(roleService.createRole(role), HttpStatus.CREATED);
	}

	@PutMapping("/{roleId}")
	public ResponseEntity<Role> updateroles(@PathVariable("roleId") Integer roleId, @RequestBody Role role) {
		return new ResponseEntity<Role>(roleService.updateRole(roleId, role), HttpStatus.OK);
	}

	@DeleteMapping("/{roleId}")
	public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Integer roleId) {
		roleService.deleteRole(roleId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{roleName}/users")
	public ResponseEntity<List<User>> getUsersByRole(@PathVariable("roleName") String roleName) {
		return new ResponseEntity<List<User>>(roleService.getUsersByRole(roleName), HttpStatus.OK);
	}
}
