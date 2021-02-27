package fr.miage.sid.agentinternaute.api;

import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

	private final SearchService service;
	
	private final ProfileService profilService;
	
	//TODO here send profile + type + title
	// without nom, age, sexe, les preferredTrucs (pas le temps ni le budget, c'est pas leur affaires).
	@GetMapping
	public ResponseEntity<?> searchArt(@RequestParam(value="title", required=false) String title, @RequestParam(value="type") String type) {
		
		if (title != null && type != null) {
			
			// TODO return list of results and not title
			return ResponseEntity.status(200).body(title);
		} else {
			
			//Send message distributeurs and get similars results
			return ResponseEntity.status(404).body("mot clé inconnu");
		}
	}
}
