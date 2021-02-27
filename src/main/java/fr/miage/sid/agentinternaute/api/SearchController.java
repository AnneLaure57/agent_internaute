package fr.miage.sid.agentinternaute.api;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.sid.agentinternaute.dto.SearchDTO;
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
	
	@GetMapping
	//public ResponseEntity<?> search(@RequestParam(value="title") String title, @RequestParam(value="type") List<Boolean> type, @RequestParam(value="Agent") Profile profil) {
	public ResponseEntity<?> search(@RequestParam SearchDTO newSearch) {
		LOGGER.info("GET on /search?title= &type= &Agent=");
		if (newSearch != null) {
			String title = newSearch.getSearchField();
			Boolean movies = newSearch.getMovies();
			Boolean musics = newSearch.getMusics();
			Boolean tv_shows = newSearch.getTv_shows();
			Profile profil = newSearch.getProfile();
			LOGGER.severe(title + movies + musics + tv_shows + profil);
			//send to distrib agent infos
			//service.search(title,type,profil);
			// TODO return list of results and not title
			return ResponseEntity.status(200).body(title);
		} else {
			return ResponseEntity.status(404).body("mot clé inconnu");
		}
	}
}
