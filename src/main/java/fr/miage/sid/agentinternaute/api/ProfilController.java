package fr.miage.sid.agentinternaute.api;

import java.net.URI;
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
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Profile.class)
@RequiredArgsConstructor
@CrossOrigin 
public class ProfilController {

	private static final Logger LOGGER = Logger.getLogger(ProfilController.class.getName());

	private final ProfileService service;

	@GetMapping
	public ResponseEntity<?> getProfileByName(@RequestParam(value = "name", required = true) String name) {
		JadeAgentContainer.getInstance().createNewAgentInternaute(name);
		return Optional.ofNullable(service.getProfileByName(name)).filter(Optional::isPresent)
				.map(p -> ResponseEntity.ok(p.get())).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getProfileById(@PathVariable Integer id) {
		Optional<Profile> profile = service.getProfileById(id);
		if(profile.isPresent()) {
			JadeAgentContainer.getInstance().createNewAgentInternaute(profile.get().getName());
			return ResponseEntity.ok(profile.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> createProfile(@RequestBody Profile profile) {
		Optional<Profile> profileOptional = service.getProfileByName(profile.getName());

		if (profileOptional.isPresent())
			return ResponseEntity.status(209).body("Profile already exists");
		else {
			Profile savedProfile = service.createOrUpdateProfile(profile);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedProfile.getId()).toUri();
			
			JadeAgentContainer.getInstance().createNewAgentInternaute(savedProfile.getName());
			
			return ResponseEntity.status(201).location(location).body(savedProfile);
		}
	}

	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @PathVariable int id) {
		Optional<Profile> profileOptional = service.getProfileById(id);

		if (!profileOptional.isPresent())
			return ResponseEntity.notFound().build();

		profile.setId(id);

		Profile savedProfile = service.createOrUpdateProfile(profile);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProfile.getId()).toUri();

		return ResponseEntity.status(200).location(location).body(savedProfile);
	}
}
