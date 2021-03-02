package fr.miage.sid.agentinternaute.api;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.JSONArray;
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

import fr.miage.sid.agentinternaute.dto.SearchDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import fr.miage.sid.agentinternaute.service.InternalComService;
import fr.miage.sid.agentinternaute.service.ProfileService;
import fr.miage.sid.agentinternaute.service.PurchaseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

	@SuppressWarnings("unused")
	private final InternalComService serviceInternal;
	private final ProfileService profileService;
	private final PurchaseService purchaseService;

	@GetMapping
	public ResponseEntity<?> getPurchasesProfile(@PathVariable Integer id) {
		LOGGER.info("GET purchase in /search");

		List<Purchase> purchases = purchaseService.findPurchasesProfile(id);
		System.out.println("length : " + purchases.toArray().length);
		return ResponseEntity.ok(purchases);
	}

	@SuppressWarnings("unused")
	@PostMapping
	public ResponseEntity<?> search(@RequestBody SearchDTO newSearch) {
		LOGGER.info("GET on /search");
		if (newSearch != null) {
			Optional<Profile> profile = profileService.getProfileById(newSearch.getProfileId());
			if(profile.isPresent()) {
				String title = newSearch.getSearchField();
				Boolean movies = newSearch.getMovies();
				Boolean musics = newSearch.getMusics();
				Boolean tv_shows = newSearch.getTvShows();
				System.out.println("Je suis" + newSearch);
				
				//send to distrib agent infos
				String result = serviceInternal.sendSearchTitleToAgent(title,movies,musics,tv_shows, profile.get());
				
				// TODO return list of results and not null
				
				// Mock response
				/*JSONArray response = new JSONArray();
//				JSONObject distributor = new JSONObject();
//				distributor.put("distributor", "d1");
//				response.put(distributor);
				JSONObject movie1 = new JSONObject();
				movie1.put("id", "1006");
				movie1.put("titre", "Harry Potter et la chambre des secrets");
				movie1.put("description", "Alors que l\u0027oncle Vernon, la tante Pétunia et son cousin Dudley re?oivent d\u0027importants invités ? d?ner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l\u0027école de Poudlard et qu\u0027il ne doit pas y retourner en septembre. Harry refuse de le croire.\u003cbr /\u003eMais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle-ci lui dit que la redoutable et légendaire Chambre des secrets est ? nouveau ouverte, permettant ainsi ? l\u0027héritier de Serpentard de semer le chaos ? Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succ?dent dans les couloirs de l\u0027école, sans que les professeurs - pas m?me le populaire Gilderoy Lockhart - ne parviennent ? endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.");
				movie1.put("prix", 5);
				movie1.put("dateSortie", 2002);
				movie1.put("note", 3.5);
				
				JSONArray genres = new JSONArray();
				JSONObject genre1 = new JSONObject();
				genre1.put("id", "6");
				genre1.put("nom", "Fantastique");
				movie1.put("genres", genres);
				
				JSONArray acteurs = new JSONArray();
				JSONObject acteur1 = new JSONObject();
				acteur1.put("id", "57530");
				acteur1.put("nom", "Watson");
				acteur1.put("prenom", "Emma");
				acteurs.put(acteur1);
				JSONObject acteur2 = new JSONObject();
				acteur2.put("id", "29169");
				acteur2.put("nom", "Grint");
				acteur2.put("prenom", "Rupert");
				acteurs.put(acteur2);
				JSONObject acteur3 = new JSONObject();
				acteur3.put("id", "36582");
				acteur3.put("nom", "Radcliffe");
				acteur3.put("prenom", "Daniel");
				acteurs.put(acteur3);
				movie1.put("acteurs", acteurs);
				response.put(movie1);
				
				JSONArray realisateurs = new JSONArray();
				JSONObject realisateur1 = new JSONObject();
				realisateur1.put("id", "7670");
				realisateur1.put("nom", "Columbus");
				realisateur1.put("prenom", "Chris");
				realisateurs.put(realisateur1);
				movie1.put("realisateurs", realisateurs);
				
				JSONObject movie2 = new JSONObject();
				movie2.put("id", "1033");
				movie2.put("titre", "Rox et Rouky");
				movie2.put("description", "Rox le renard et Rouky le chien sont les meilleurs amis du monde. Mais cette amitié est menacée lorsque le ma?tre de Rouky devient chasseur...");
				movie2.put("dateSortie", 1981);
				
				JSONArray genres2 = new JSONArray();
				JSONObject genre21 = new JSONObject();
				genre21.put("id", "7");
				genre21.put("nom", "Animation");
				movie2.put("genres", genres2);
				
				JSONArray acteurs2 = new JSONArray();
				JSONObject acteur21 = new JSONObject();
				acteur21.put("id", "2316");
				acteur21.put("nom", "Rooney");
				acteur21.put("prenom", "Mickey");
				acteurs2.put(acteur21);
				JSONObject acteur22 = new JSONObject();
				acteur22.put("id", "1475");
				acteur22.put("nom", "Russell");
				acteur22.put("prenom", "Kurt");
				acteurs2.put(acteur22);
				JSONObject acteur23 = new JSONObject();
				acteur23.put("id", "1000250");
				acteur23.put("nom", "Salez");
				acteur23.put("prenom", "Morvan");
				acteurs2.put(acteur23);
				movie2.put("acteurs", acteurs2);
				
				JSONArray realisateurs2 = new JSONArray();
				JSONObject realisateur21 = new JSONObject();
				realisateur21.put("id", "114082");
				realisateur21.put("nom", "Rich");
				realisateur21.put("prenom", "Richard");
				realisateurs2.put(realisateur21);
				movie2.put("realisateurs", realisateurs2);
				response.put(movie2);*/
				
				return ResponseEntity.status(200).body("WAIT");
				//return ResponseEntity.status(200).body(response.toString());
			}
		}

		return ResponseEntity.badRequest().build();
	}
}
