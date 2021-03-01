package fr.miage.sid.agentinternaute.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.entity.Actor;
import fr.miage.sid.agentinternaute.entity.Artist;
import fr.miage.sid.agentinternaute.entity.Director;
import fr.miage.sid.agentinternaute.entity.Genre;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());

	private final PurchaseRepository repo;

	public Optional<Purchase> getPurchaseById(int ID) {
		LOGGER.info("Get purchase by ID " + ID);
		return repo.findById(ID);
	}

	public List<Purchase> getPurchaseByDate(Date date, int profileID) {
		LOGGER.info("Get purchase by date " + date);
		return repo.findByViewDateAndProfileId(date, profileID);
	}

	public List<Purchase> getPurchaseByRating(Double rating, int profileID) {
		LOGGER.info("Get purchase by rating " + rating + "(profile ID : " + profileID + ")");
		return repo.findByItemRatingAndProfileId(rating, profileID);
	}

	public Purchase createPurchase(PurchaseDTO p, Profile profile) {
		Purchase purchase = new Purchase(p.getItemType(), p.getId(), p.getPrix(), p.getTitre(), p.getDescription(),
				p.getDateSortie(), p.getNote(), p.getDistributeur(), p.getProducteur(), p.getGenres(), p.getActeurs(),
				p.getRealisateurs(), p.getArtistes(), profile);
		repo.save(purchase);
		return purchase;
	}

	public Purchase updatePurchase(Purchase updated) {
		LOGGER.info("Update purchase for " + updated.getTitre());
		Optional<Purchase> opt = getPurchaseById(updated.getId());
		if (opt.isPresent()) {
			Purchase purchase = opt.get();
			purchase.setItemRating(updated.getItemRating());
			purchase.setDistributeurRating(updated.getDistributeurRating());
			purchase.setProducteurRating(updated.getProducteurRating());
			purchase.setArtistes(updated.getArtistes());
			purchase.setActeurs(updated.getActeurs());
			purchase.setRealisateurs(updated.getRealisateurs());

			repo.save(purchase);
			return purchase;
		}
		return null;
	}

	public Iterable<Purchase> findPaged(int page, int size) {

		// TODO : LOGGER
		if (page < 0)
			page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}

	public List<Purchase> findPurchasesProfile(Integer profileId) {

		return repo.findByProfileId(profileId);
	}
}
