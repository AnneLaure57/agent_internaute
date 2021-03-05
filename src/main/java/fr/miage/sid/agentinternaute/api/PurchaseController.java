package fr.miage.sid.agentinternaute.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.InternalComService;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Purchase.class)
@RequiredArgsConstructor
@CrossOrigin 
public class PurchaseController {

	private static final Logger LOGGER = Logger.getLogger(PurchaseController.class.getName());
	private final PurchaseService service;
	private final ProfileService serviceProfile;
	private final InternalComService agentService;
	
	@GetMapping(value = "/{id}") 
	public ResponseEntity<?> getPurchasesProfile(@PathVariable Integer id) {
	LOGGER.info("GET on /purchases with profile id : " + id);
	
	List<Purchase> purchases = service.findPurchasesProfile(id);
		return  ResponseEntity.ok(purchases);
	}
			
	@SuppressWarnings("unused")
	@PostMapping
	@Transactional
    public ResponseEntity<?> create(@RequestBody PurchaseDTO p) {
		LOGGER.info("POST on /purchases for profile " + p.getProfileId());
		
		Optional<Profile> profile = serviceProfile.getProfileById(p.getProfileId());
		if(profile.isPresent()) {			
			// Send to distributor
			JSONObject response = agentService.sendAcceptProposalToAgent(profile.get().getName(), p);
				
			if(response != null) {
				// Persist infos
				Purchase savedPurchase = service.createPurchase(p, profile.get());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
						.buildAndExpand(savedPurchase.getId()).toUri();
				
				// Send response to to UI
				return ResponseEntity.ok().body(response);
			} else {
				return ResponseEntity.notFound().build();
			}	
		}
		return ResponseEntity.badRequest().build();
	}

    @PutMapping(value = "/{id}")
    public Purchase update(@RequestBody Purchase h, @PathVariable int id) {
    	LOGGER.info("PUT on /purchases/"+id);
        return null;
    }
}
