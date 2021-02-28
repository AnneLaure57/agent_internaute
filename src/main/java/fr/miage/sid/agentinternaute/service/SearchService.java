package fr.miage.sid.agentinternaute.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.dto.ResultDTO;
import fr.miage.sid.agentinternaute.dto.SearchDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.repository.ProfileRepository;
import jade.util.Logger;
import jade.wrapper.AgentContainer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private final static String MOVIES = "movies", MUSICS = "musics", TV_SHOWS = "tv_shows";
	
	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private final ProfileRepository repo;
	
	private HashMap<String, String> searchMap;
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	public Iterable<Profile> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
	
	public List<ResultDTO> search(String title, Boolean movies, Boolean musics, Boolean tv_shows, Profile profil) {
		// put in JSON Object
		// without nom, age, sexe, les preferredTrucs type + title
		this.searchMap = new HashMap<String, String>();
		System.out.println("fuck IA" + profil);

		// we need it to say what we want
		//this.searchMap.put("request", ACLMessageTypes.REQUEST_SEARCH);
		
		// what we search
		this.searchMap.put("title", title);
		
		// send our profile
		this.searchMap.put("name", profil.getName());
		this.searchMap.put("age", Integer.toString(profil.getAge()));
		this.searchMap.put("sex", profil.getSex());
		
		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (movies) checkTypes.add(MOVIES);
		if (musics)	checkTypes.add(MUSICS);
		if (tv_shows) checkTypes.add(TV_SHOWS);
		this.searchMap.put("types", Arrays.toString(checkTypes.toArray()));
		
		// TODO => OPTIMISE get preferences list => actors, directors, musics etc.
		this.searchMap.put("preferences_actors", profil.getPreferedActors().toString());
		this.searchMap.put("preferences_directors", profil.getPreferedDirectors().toString());
		this.searchMap.put("preferences_musics_artists", profil.getPreferedMusicArtists().toString());
		this.searchMap.put("preferences_musics_genres", profil.getPreferedMusicGenres().toString());
		this.searchMap.put("preferences_videos", profil.getPreferedVideoGenres().toString());
		
		JSONObject searchInformations = new JSONObject(searchMap);
		
		try {
			// On envoie tout à nos agents distributeurs sous JSON Object
//            AgentContainer agentContainer = JadeAgentContainer.getInstance().getAgentContainer();
//            agentContainer.getAgent("distributeur").putO2AObject("fr.miage.sid.agentinternaute.agent.AgentInternaute" + searchInformations, false);
//            agentContainer.getAgent("distributeur").putO2AObject(this, false);
            
            // TODO How send to distrib with function with function sendMessageJSON in agentInternaute
        } catch (Exception e) {
            e.printStackTrace();
        }
		// need timer ?
		return null;
    }
}
