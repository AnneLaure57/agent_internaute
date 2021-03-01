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

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.dto.RatingsDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.InternalComService;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import jade.core.Agent;
import jade.util.Event;
import jade.wrapper.AgentController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class RatingsController {

	private static final Logger LOGGER = Logger.getLogger(RatingsController.class.getName());
	private final PurchaseService service;
	private final ProfileService profileService;
	private final InternalComService agentService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> send(@RequestBody Purchase purchase) {
		LOGGER.info("POST on /ratings");
		if (purchase != null) {
			// Get full purchase with profile
			Optional<Purchase> fullpurchase = service.getPurchaseById(purchase.getId());
			if (fullpurchase.isPresent()) {
				// Get agent name from full purchase
				String agentName = fullpurchase.get().getProfile().getName();
				
				// Update purchase
				service.updatePurchase(purchase);

				// Send to erepute
				String response = agentService.sendRatingsToAgent(agentName, purchase);
				return ResponseEntity.ok().body(response);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
