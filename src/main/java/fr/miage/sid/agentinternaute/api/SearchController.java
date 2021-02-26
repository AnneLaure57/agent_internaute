package fr.miage.sid.agentinternaute.api;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.apache.tomcat.util.json.JSONParser;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@Transactional
	public ResponseEntity<?> searchArt(@RequestBody String title, Boolean type, Profile profile) {
		Optional<Profile> profileOptional = profilService.getProfileByName(profile.getName());

		if (profileOptional.isPresent())
			return ResponseEntity.status(209).body("Profile already exists");
		else {
			// set the fields to not send to distributeur
			// DO NOT save the new profile
			profile.setAverageConsumptionTime(null);
			profile.setCurrentConsumptionTime(null);
			profile.setCurrentExpenses(0);
			
			//convert String -> JSON Array
			JSONArray array = new JSONArray();
			
			// Not retturn profil but message from distribteur here/request
			return ResponseEntity.status(201).body(profile);
		}
	}
}
