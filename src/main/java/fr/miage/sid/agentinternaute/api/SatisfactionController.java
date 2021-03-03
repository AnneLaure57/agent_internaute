package fr.miage.sid.agentinternaute.api;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.dto.SatisfactionDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Satisfaction;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.SatisfactionService;
import lombok.RequiredArgsConstructor;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
@RestController
@RequestMapping(value = "/satisfaction", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Satisfaction.class)
@RequiredArgsConstructor
@CrossOrigin 
public class SatisfactionController {

	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final Logger LOGGER = Logger.getLogger(SatisfactionController.class.getName());
	
	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final SatisfactionService service;
	private final ProfileService serviceProfile;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	/* ========================================= Methodes ============================================== */ /*=========================================*/

	@GetMapping() 
	public ResponseEntity<?> getPurchasesProfile(@PathVariable Integer ID) {
	LOGGER.info("GET on /satisfactions with profile ID : " + ID);
	
	List<Satisfaction> satisfactions = service.findSatisfactionsProfile(ID);
		return  ResponseEntity.ok(satisfactions);
	}
			
	@PostMapping
	@Transactional
    public ResponseEntity<?> create(@RequestBody SatisfactionDTO satisfaction) {
		LOGGER.info("POST on /satisfactions for profile ID " + satisfaction.getProfileId());
		Optional<Profile> profile = serviceProfile.getProfileById(satisfaction.getProfileId());
		
		if(profile.isPresent()) {			
			// Persist infos
			Satisfaction savedSatisfaction = service.createSatisfaction(profile.get(), satisfaction.getMeanSatisfaction());
			
			// Send response to to UI
			return ResponseEntity.ok().body(savedSatisfaction);
		} else {
				return ResponseEntity.notFound().build();
		}
	}
	
	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/

}
