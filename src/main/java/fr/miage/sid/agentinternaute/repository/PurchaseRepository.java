package fr.miage.sid.agentinternaute.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.sid.agentinternaute.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

	Optional<Purchase> findById(int id);
	List<Purchase> findByViewDateAndProfileId(Date date, int profileId);
	List<Purchase> findByItemRatingAndProfileId(Double rating, int profileId);
	List<Purchase> findByProfileId(Integer profileId);
}