package fr.miage.sid.agentinternaute.api;

import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.entity.History;
import fr.miage.sid.agentinternaute.service.CommonDBService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/db", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(History.class)
@RequiredArgsConstructor
@CrossOrigin 
public class CommonDBController {

	private static final Logger LOGGER = Logger.getLogger(CommonDBController.class.getName());

	private final CommonDBService service;

	@GetMapping(value = "/actors")
	public ResponseEntity<?> getActors() {
		JSONArray actors = service.getActors();
		return ResponseEntity.status(200).body(actors);
		
	}
	
	@GetMapping(value = "/directors")
	public ResponseEntity<?> getDirectors() {
		JSONArray directors = service.getDirectors();
		return ResponseEntity.status(200).body(directors);
	}
	
	@GetMapping(value = "/artists")
	public ResponseEntity<?> getArtists() {
		JSONArray artists = service.getArtists();
		return ResponseEntity.status(200).body(artists);
	}
	
	@GetMapping(value = "/video_genres")
	public ResponseEntity<?> getVideoGenres() {
		JSONArray video_genres = service.getVideoGenres();
		return ResponseEntity.status(200).body(video_genres);
	}
	
	@GetMapping(value = "/music_genres")
	public ResponseEntity<?> getMusicGenres() {
		JSONArray music_genres = service.getMusicGenres();
//		System.out.println("---------------Genre de musique----------------");
//		System.out.println(music_genres.toString());
		return ResponseEntity.status(200).body(music_genres.toString());
	}
}
