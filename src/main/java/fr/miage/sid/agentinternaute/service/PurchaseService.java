package fr.miage.sid.agentinternaute.service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());
	
	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final PurchaseRepository repo;

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/

	public Optional<Purchase> getPurchaseById(int id, String profileId) {
		return repo.findByIdAndProfileId(id, profileId);
	}

	public Optional<Purchase> getPurchaseByName(Date date, String profileId) {
		return repo.findByDateAndProfileId(date, profileId);
	}

	public Optional<Purchase> getPurchaseByRating(Double rating, String profileId) {
		return repo.findByRatingAndProfileId(rating, profileId);
	}

	public Purchase createOrUpdatePurchase(Purchase purchase) {
		repo.save(purchase);
		return purchase;
	}

	public Iterable<Purchase> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}
