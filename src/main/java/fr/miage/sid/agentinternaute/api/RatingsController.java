package fr.miage.sid.agentinternaute.api;

import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.dto.RatingsDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin 
public class RatingsController {

	private static final Logger LOGGER = Logger.getLogger(RatingsController.class.getName());
	private final PurchaseService service;
			
	@PostMapping
	@Transactional
    public ResponseEntity<?> send(@RequestBody RatingsDTO r) {
		LOGGER.info("POST on /ratings");
		Optional<Purchase> purchase = service.getPurchaseById(r.getPurchaseId());
		if(purchase.isPresent()) {
			// Update purchase
			Purchase p = purchase.get();
			p.setMediumRating(r.getMediumRating());
			p.setDistributorRating(r.getDistributorRating());
			p.setProductorRating(r.getProductorRating());
			p.setActorsRating(r.getActorsRating());
			p.setDirectorsRating(r.getDirectorsRating());
			service.updatePurchase(p);
			
			// Send to erepute TODO
			
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
