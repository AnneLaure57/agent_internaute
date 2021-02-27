package fr.miage.sid.agentinternaute.api;

import java.util.logging.Logger;

import org.json.JSONArray;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.CommonDBService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/db", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Purchase.class)
@RequiredArgsConstructor
@CrossOrigin 
public class CommonDBController {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final Logger LOGGER = Logger.getLogger(CommonDBController.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final CommonDBService service;

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/
	
	@GetMapping(value = "/actors")
	public ResponseEntity<?> getActors() {
		LOGGER.info("GET on /db/actors");
		JSONArray actors = service.getActors();
		return ResponseEntity.status(200).body(actors.toString());
	}
	
	@GetMapping(value = "/directors")
	public ResponseEntity<?> getDirectors() {
		LOGGER.info("GET on /db/directors");
		JSONArray directors = service.getDirectors();
		return ResponseEntity.status(200).body(directors.toString());
	}
	
	@GetMapping(value = "/artists")
	public ResponseEntity<?> getArtists() {
		LOGGER.info("GET on /db/artists");
		JSONArray artists = service.getArtists();
		return ResponseEntity.status(200).body(artists.toString());
	}
	
	@GetMapping(value = "/video_genres")
	public ResponseEntity<?> getVideoGenres() {
		LOGGER.info("GET on /db/video_genres");
		JSONArray video_genres = service.getVideoGenres();
		return ResponseEntity.status(200).body(video_genres.toString());
	}
	
	@GetMapping(value = "/music_genres")
	public ResponseEntity<?> getMusicGenres() {
		LOGGER.info("GET on /db/music_genres");
		JSONArray music_genres = service.getMusicGenres();
		return ResponseEntity.status(200).body(music_genres.toString());
	}

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}
