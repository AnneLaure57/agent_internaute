package fr.miage.sid.agentinternaute.api;

import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	// TODO : fix warnings
	@SuppressWarnings("unused")
	private final ProfileService profilService;

	// TODO : fix warnings
	@SuppressWarnings("unused")
	@PostMapping
	public ResponseEntity<?> search(@RequestBody SearchDTO newSearch) {
		LOGGER.info("GET on /search");
		if (newSearch != null) {
			String title = newSearch.getSearchField();
			Boolean movies = newSearch.getMovies();
			Boolean musics = newSearch.getMusics();
			Boolean tv_shows = newSearch.getTvShows();
			Profile profil = newSearch.getProfile();
			System.out.println("Je suis" + newSearch);
			//send to distrib agent infos
			service.search(title,movies,musics,tv_shows,newSearch.getProfile());
			// TODO return list of results and not title
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
