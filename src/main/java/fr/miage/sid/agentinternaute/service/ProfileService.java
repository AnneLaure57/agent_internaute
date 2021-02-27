package fr.miage.sid.agentinternaute.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final ProfileRepository repo;

	public Optional<Profile> getProfileById(int id) {
		return repo.findById(id);
	}

	public Optional<Profile> getProfileByName(String name) {
		return repo.findByName(name);
	}

	public Profile createProfile(Profile profile) {
		Profile newProfile = new Profile(profile.getName(), profile.getSex(), profile.getAge(),
				profile.getAverageConsumptionTime(), profile.getMaxBudget());
		repo.save(newProfile);
		return newProfile;
	}

	public Profile updateProfile(Profile profile) {
		System.out.println(profile);
		repo.save(profile);
		return profile;
	}
}