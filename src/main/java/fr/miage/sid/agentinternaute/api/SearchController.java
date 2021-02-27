package fr.miage.sid.agentinternaute.api;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.SearchService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;

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
	public ResponseEntity<?> searchArt(@RequestParam(value="title", required=false) String title, @RequestParam(value="type") String type, @RequestParam(value="Agent") Profile profil) {
		LOGGER.info("GET on /search?title= &type= &Agent=");
		if (title != null && type != null) {
			service.search(title,type,profil);
			// TODO return list of results and not title
			return ResponseEntity.status(200).body(title);
		} else {
			return ResponseEntity.status(404).body("mot clé inconnu");
		}
	}
}
