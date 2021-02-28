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
	/* ========================================= Global ================================================ */ /*=========================================*/

	private final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final PurchaseRepository repo;

	/* ========================================= Methodes ============================================== */ /*=========================================*/

	public Optional<Purchase> getPurchaseById(int id) {
		LOGGER.info("Get purchase by ID " + id);
		return repo.findById(id);
	}

	public List<Purchase> getPurchaseByDate(Date date, int profileId) {
		LOGGER.info("Get purchase by date " + date);
		return repo.findByViewDateAndProfileId(date, profileId);
	}

	public List<Purchase> getPurchaseByRating(Double rating, int profileId) {
		LOGGER.info("Get purchase by rating " + rating + "(profile ID : " + profileId + ")");
		return repo.findByRatingAndProfileId(rating, profileId);
	}

	public Purchase createPurchase(PurchaseDTO p, Profile profile) {
		Purchase purchase = new Purchase(p.getItemId(), p.getItemTitle(), p.getDistributorId(), p.getProductorId(), p.getActorsIds(), p.getDirectorsIds(), profile);
		repo.save(purchase);
		return purchase;
	}
	
	public Purchase updatePurchase(Purchase purchase) {
		LOGGER.info("Update purchase for " + purchase.getItemTitle());
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
}
