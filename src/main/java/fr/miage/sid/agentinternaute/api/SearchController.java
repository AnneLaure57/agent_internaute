package fr.miage.sid.agentinternaute.api;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

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

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

	private final SearchService service;
	
	private final ProfileService profilService;
	
	//TODO here send profile + type + title
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> searchArt(@RequestBody String title, Boolean type, Profile profile) {
		Optional<Profile> profileOptional = profilService.getProfileByName(profile.getName());
		return null;

		/*if (profileOptional.isPresent())
			return ResponseEntity.status(209).body("Profile already exists");
		else {
			Profile savedProfile = service.createOrUpdateProfile(profile);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedProfile.getId()).toUri();
			
			JadeAgentContainer.getInstance().createNewAgentInternaute(savedProfile.getName());
			
			return ResponseEntity.status(201).location(location).body(savedProfile);
		}*/
	}


}
