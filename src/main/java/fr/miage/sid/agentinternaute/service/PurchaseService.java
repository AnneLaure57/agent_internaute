package fr.miage.sid.agentinternaute.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());
	private final PurchaseRepository repo;

	public Optional<Purchase> getPurchaseById(int id, int profileId) {
		LOGGER.info("Get purchase by ID " + id);
		return repo.findByIdAndProfileId(id, profileId);
	}

	public Optional<Purchase> getPurchaseByName(Date date, int profileId) {
		// TODO : LOGGER
		return repo.findByDateAndProfileId(date, profileId);
	}

	public Optional<Purchase> getPurchaseByRating(Double rating, int profileId) {
		// TODO : LOGGER
		return repo.findByRatingAndProfileId(rating, profileId);
	}

	public Purchase createPurchase(PurchaseDTO p, Profile profile) {
		Purchase purchase = new Purchase(p.getRating(), p.getItemId(), p.getItemTitle(), profile);
		repo.save(purchase);
		return purchase;
	}

	public Iterable<Purchase> findPaged(int page, int size) {
		// TODO : LOGGER
		if (page < 0)
			page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
	
	public List<Purchase> findPurchasesProfile(Integer id) {
		
		return repo.findByProfileId(id);
	}

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/

}
