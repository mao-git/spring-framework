package com.devs4j.users.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.users.entities.Address;
import com.devs4j.users.entities.Profile;
import com.devs4j.users.repository.AddressRepository;
import com.devs4j.users.repository.ProfileRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public List<Address> findAddressByProfileIdAndUserId(Integer userId, Integer profileId) {
		return addressRepository.findByProfileId(userId, profileId);
	}

	public Address create(Integer userId, Integer profileId, Address address) {
		Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Profile %d and user %d not found", profileId, userId));
		}

		address.setProfile(result.get());
		return addressRepository.save(address);
	}
}
