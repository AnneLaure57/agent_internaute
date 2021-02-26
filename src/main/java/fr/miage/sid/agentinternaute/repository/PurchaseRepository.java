package fr.miage.sid.agentinternaute.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.sid.agentinternaute.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

	Optional<Purchase> findByIdAndProfileId(int id, String profileId);
	Optional<Purchase> findByDateAndProfileId(Date date, String profileId);
	Optional<Purchase> findByRatingAndProfileId(Double rating, String profileId);
}