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
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.dto.SearchDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.InternalComService;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
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
	public ResponseEntity<?> search(@RequestBody SearchDTO newSearch) {
		LOGGER.info("GET on /search");
		if (newSearch != null) {
			
			Optional<Profile> profile = profileService.getProfileById(newSearch.getProfileId());
			
			if(profile.isPresent()) {
				String title = newSearch.getSearchField();
				Boolean movies = newSearch.getMovies();
				Boolean musics = newSearch.getMusics();
				Boolean tv_shows = newSearch.getTvShows();
				System.out.println("Controleur -> recherche : " + newSearch);
				
				// Send request to internaute agent
				JSONArray response = serviceInternal.sendSearchTitleToAgent(title,movies,musics,tv_shows, profile.get());
				if(response != null) {
					return ResponseEntity.status(200).body(response.toString());
				}
				return ResponseEntity.notFound().build();
			}
		}

		return ResponseEntity.badRequest().build();
	}
}
