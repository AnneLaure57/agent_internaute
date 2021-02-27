package fr.miage.sid.agentinternaute.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final Logger LOGGER = Logger.getLogger(ProfileService.class.getName());
	private final ProfileRepository repo;
	
	public Optional<Profile> getProfileById(int id) {
		LOGGER.info("Get profile by ID " + id);
		return repo.findById(id);
	}

	public Optional<Profile> getProfileByName(String name) {
		LOGGER.info("Get profile by name " + name);
		return repo.findByName(name);
	}

	public Profile createProfile(Profile profile) {
		LOGGER.info("Create profile for " + profile.getName());
		Profile newProfile = new Profile(profile.getName(), profile.getSex(), profile.getAge(),
				profile.getAverageConsumptionTime(), profile.getMaxBudget());
		repo.save(newProfile);
		return newProfile;
	}

	public Profile updateProfile(Profile profile) {
		LOGGER.info("Update profile for " + profile.getName());
		repo.save(profile);
		return profile;
	}	
}