package com.devs4j.users.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.users.entities.Profile;
import com.devs4j.users.entities.User;
import com.devs4j.users.repository.ProfileRepository;
import com.devs4j.users.repository.UserRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	public Profile create(Integer userId, Profile profile) {
		Optional<User> userResult = userRepository.findById(userId);
		if (userResult.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId));
		}
		profile.setUser(userResult.get());
		return profileRepository.save(profile);
	}

	public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
		return profileRepository.findByUserIdAndProfileId(userId, profileId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("Profile not found for user %d and profile %d", userId, profileId)));
	}
}
