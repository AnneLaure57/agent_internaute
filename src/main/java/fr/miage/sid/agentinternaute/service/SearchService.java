package fr.miage.sid.agentinternaute.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.dto.ResultDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final static String MOVIES = "movies", MUSICS = "musics", TV_SHOWS = "tv_shows";
	
	private final ProfileRepository repo;
	
	private HashMap<String, String> searchMap;

	private HashMap<String, String> profilMap;
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	public Iterable<Profile> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
	
	public List<ResultDTO> search(String title, Boolean movies, Boolean musics, Boolean tv_shows, Profile profil) {
		// put in JSON Object
		// without nom, age, sexe, les preferredTrucs type + title
		this.searchMap = new HashMap<String, String>();
		this.profilMap = new HashMap<String, String>();
		System.out.println("fuck IA" + profil);

		// we need it to say what we want
		this.searchMap.put("request", ACLMessageTypes.REQUEST_SEARCH);
		
		// what we search
		this.searchMap.put("title", title);

		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (movies) checkTypes.add(MOVIES);
		if (musics)	checkTypes.add(MUSICS);
		if (tv_shows) checkTypes.add(TV_SHOWS);
		this.searchMap.put("types", Arrays.toString(checkTypes.toArray()));
		
		// send our profile
		this.profilMap.put("name", profil.getName());
		this.profilMap.put("age", Integer.toString(profil.getAge()));
		this.profilMap.put("sex", profil.getSex());
		// TODO => OPTIMISE get preferences list => actors, directors, musics etc.
		this.profilMap.put("prefered_actors", profil.getPreferedActors().toString());
		this.profilMap.put("prefered_directors", profil.getPreferedDirectors().toString());
		this.profilMap.put("prefered_musics_artists", profil.getPreferedMusicArtists().toString());
		this.profilMap.put("prefered_musics_genres", profil.getPreferedMusicGenres().toString());
		this.profilMap.put("prefered_videos", profil.getPreferedVideoGenres().toString());
		//add Profil in search Map
		this.searchMap.put("profil_utilisateur", profilMap.toString());
		
		
		JSONObject searchInformations = new JSONObject(searchMap);
		
		System.out.println(searchInformations.toString());
		
		try {
			// On envoie tout à nos agents distributeurs sous JSON Object
//            AgentContainer agentContainer = JadeAgentContainer.getInstance().getAgentContainer();
//            agentContainer.getAgent("distributeur").putO2AObject("fr.miage.sid.agentinternaute.agent.AgentInternaute" + searchInformations, false);
//            agentContainer.getAgent("distributeur").putO2AObject(this, false);
            
//            {
//            	"request": "search",
//            	
//            	"title": "Le Parrain",
//            	
//            	"name": "Jean",
//            	"age": "24",
//            	"sex": "Beau mâle",
//            	        	
//            	"types": "[movies, ]",
//            }
            
            // TODO How send to distrib with function with function sendMessageJSON in agentInternaute
        } catch (Exception e) {
            e.printStackTrace();
        }
		// need timer ?
		return null;
    }
}
