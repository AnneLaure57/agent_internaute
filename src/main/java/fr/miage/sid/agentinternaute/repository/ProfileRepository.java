package fr.miage.sid.agentinternaute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.sid.agentinternaute.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {

	Optional<Profile> findById(int id);

	Optional<Profile> findByName(String name);

}
