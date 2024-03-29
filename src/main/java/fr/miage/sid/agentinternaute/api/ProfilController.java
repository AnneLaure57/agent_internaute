package fr.miage.sid.agentinternaute.api;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.service.ProfileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/profil", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Profile.class)
@RequiredArgsConstructor
@CrossOrigin 
public class ProfilController {

	private static final Logger LOGGER = Logger.getLogger(ProfilController.class.getName());
	private final ProfileService service;

	@GetMapping
	public ResponseEntity<?> getProfileByName(@RequestParam(value = "name", required = true) String name) {
		LOGGER.info("GET on /profile?name=");
		Optional<Profile> p = service.getProfileByName(name);
		if(p.isPresent()) {			
			JadeAgentContainer.getInstance().createNewAgentInternaute(name);
			return ResponseEntity.ok(p.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getProfileById(@PathVariable Integer id) {
		LOGGER.info("GET on /profile/"+id);
		Optional<Profile> profile = service.getProfileById(id);
		if(profile.isPresent()) {
			return ResponseEntity.ok(profile.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/strategies")
	public ResponseEntity<?> getStrategies() {
		LOGGER.info("GET on /profile/strategies");
		JSONArray strategies = new JSONArray();
		JSONObject strategy1 =  new JSONObject();
		strategy1.put("name", "Econome");
		strategy1.put("description", "Veut dépenser le moins possible");
		strategies.put(strategy1);
		JSONObject strategy2 =  new JSONObject();
		strategy2.put("name", "Exigent");
		strategy2.put("description", "Veut des nouveautés pas cher");
		strategies.put(strategy2);
		JSONObject strategy3 =  new JSONObject();
		strategy3.put("name", "Streamer");
		strategy3.put("description", "Ne consomme qu'en streaming");
		strategies.put(strategy3);
		return ResponseEntity.ok(strategies.toString());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> createProfile(@RequestBody Profile profile) {
		LOGGER.info("POST on /profile");
		Optional<Profile> profileOptional = service.getProfileByName(profile.getName());

		if (profileOptional.isPresent())
			return ResponseEntity.status(209).body("Profile already exists");
		else {
			Profile savedProfile = service.createProfile(profile);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
					.buildAndExpand(savedProfile.getId()).toUri();
			
			JadeAgentContainer.getInstance().createNewAgentInternaute(savedProfile.getName());
			
			return ResponseEntity.status(201).location(location).body(savedProfile);
		}
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @PathVariable int id) {
		LOGGER.info("PUT on /profile/"+id);
		Optional<Profile> profileOptional = service.getProfileById(id);

		if (!profileOptional.isPresent())
			return ResponseEntity.notFound().build();

		profile.setId(id);

		Profile savedProfile = service.updateProfile(profile);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
				.buildAndExpand(savedProfile.getId()).toUri();

		return ResponseEntity.status(200).location(location).body(savedProfile);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> logout(@PathVariable int id) {
		LOGGER.info("DELETE on /profile/" + id + " will only kill the agent");
		Optional<Profile> profileOptional = service.getProfileById(id);
		if(profileOptional.isPresent()) {
			JadeAgentContainer.getInstance().destroyNewAgentInternaute(profileOptional.get().getName());
		}

		return ResponseEntity.ok().build();
	}
}
