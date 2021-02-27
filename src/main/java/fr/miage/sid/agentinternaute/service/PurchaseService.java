package fr.miage.sid.agentinternaute.service;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository repo;

	public Optional<Purchase> getPurchaseById(int id, String profileId) {
		return repo.findByIdAndProfileId(id, profileId);
	}

	public Optional<Purchase> getPurchaseByName(Date date, String profileId) {
		return repo.findByDateAndProfileId(date, profileId);
	}

	public Optional<Purchase> getPurchaseByRating(Double rating, String profileId) {
		return repo.findByRatingAndProfileId(rating, profileId);
	}

	public Purchase createOrUpdatePurchase(Purchase p) {
		Purchase purchase = new Purchase( p.getRating(), p.getItemId(), p.getItemTitle(), p.getProfile());
		repo.save(purchase);
		return purchase;
	}

	public Iterable<Purchase> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
}
