package fr.miage.sid.agentinternaute.service;

import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.repository.ProfileRepository;
import jade.wrapper.AgentContainer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final ProfileRepository repo;
	

	public Iterable<Profile> findPaged(int page, int size) {
		if (page < 0) page = 0;

		return repo.findAll(PageRequest.of(page, size)).getContent();
	}
	
	public void search(String title, List<Boolean> type, Profile profil) {
		//put in JSON Object
		// without nom, age, sexe, les preferredTrucs type + title
		JSONObject searchInformations = null;
		searchInformations.put("title", title);
		//tv_shows, musics etc.
		for (int i = 0; i < type.size(); i++) {
			searchInformations.put("type_"+ type.get(i), type.get(i));
		}
		searchInformations.put("name", profil.getName());
		searchInformations.put("age", profil.getAge());
		searchInformations.put("sex", profil.getSex());
		//TODO => OPTIMISE get preferences list => actors, directors, musics etc.
		searchInformations.put("preferences_actors", profil.getPreferedActors());
		searchInformations.put("preferences_directors", profil.getPreferedDirectors());
		searchInformations.put("preferences_musics_artists", profil.getPreferedMusicArtists());
		searchInformations.put("preferences_musics_genres", profil.getPreferedMusicGenres());
		searchInformations.put("preferences_videos", profil.getPreferedVideoGenres());
		
		try {
			//On envoie tout à nos agents distributeurs sous JSON Object
            AgentContainer agentContainer = JadeAgentContainer.getInstance().getAgentContainer();
            agentContainer.getAgent("distributeur").putO2AObject("fr.miage.sid.agentinternaute.agent.AgentInternaute" + searchInformations, false);
            agentContainer.getAgent("distributeur").putO2AObject(this, false);
            //TODO How send to distrib with function
        } catch (Exception e) {
            e.printStackTrace();
        }
		//need timer ? 
    }

}
