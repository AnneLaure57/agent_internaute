package fr.miage.sid.agentinternaute.api;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.agent.mock.distributeur.JSONDistributeur1;
import fr.miage.sid.agentinternaute.dto.SearchDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.InternalComService;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import fr.miage.sid.agentinternaute.strategy.Econome;
import fr.miage.sid.agentinternaute.strategy.Exigent;
import fr.miage.sid.agentinternaute.strategy.Streamer;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

	private final InternalComService serviceInternal;
	private final ProfileService profileService;
	private final PurchaseService purchaseService;

	@GetMapping
	public ResponseEntity<?> getPurchasesProfile(@PathVariable Integer id) {
		LOGGER.info("GET purchase in /search");
		List<Purchase> purchases = purchaseService.findPurchasesProfile(id);
		return ResponseEntity.ok(purchases);
	}

	@PostMapping
	public ResponseEntity<?> search(@RequestBody SearchDTO newSearch, 
			@RequestParam(value = "filter", required = false, defaultValue = "false") String filter) {
		LOGGER.info("GET on /search");
		if (newSearch != null) {
			
			Optional<Profile> profile = profileService.getProfileById(newSearch.getProfileId());
			
			if(profile.isPresent()) {
				JSONObject response = null;
				// Send request to internaute agent
				if(filter.equals("false")) {					
					response = serviceInternal.sendSearchTitleToAgent(newSearch, profile.get());
				} else {
					response = serviceInternal.sendSearchFiltersToAgent(newSearch, profile.get());
				}
				
				if(response != null) {
					JSONObject newRes = null ;
					if(profile.get().getStrategy().equals("Econome")) {
						 Econome e = new Econome();
						 newRes = e.economeResponse(response, profile.get());
					}
					
					if(profile.get().getStrategy().equals("Exigent")) {
						Exigent e = new Exigent();
						newRes = e.exigentStrategy(profile.get(), response);
					}
					
					if(profile.get().getStrategy().equals("Streamer")) {
						Streamer s = new Streamer();
						newRes = s.streamerStrategy(profile.get(), response);
					}
					
					if(response != null) {
						return ResponseEntity.status(200).body(newRes.toString());
					}
				}
				return ResponseEntity.notFound().build();
			}
		}

		return ResponseEntity.badRequest().build();
	}
}
