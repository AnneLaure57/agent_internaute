package fr.miage.sid.agentinternaute.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.entity.Profile;
import jade.wrapper.AgentContainer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	
//	private final ProfileRepository repo;
//
//	public Iterable<Profile> findPaged(int page, int size) {
//		if (page < 0) page = 0;
//
//		return repo.findAll(PageRequest.of(page, size)).getContent();
//	}
	
	public void search(String title, String type, Profile profil) {
		//put in JSON Object
		JSONObject searchInformations = null;
		searchInformations.put("titre", title);
		searchInformations.put("type", type);
		searchInformations.put("profil_utilisateur", profil);
		
		try {
			//On envoie tout à nos agents distributeurs sous JSON Object
            AgentContainer agentContainer = JadeAgentContainer.getInstance().getAgentContainer();
            agentContainer.getAgent("distributeur").putO2AObject("fr.miage.sid.agentinternaute.agent.AgentInternaute" + searchInformations, false);
            agentContainer.getAgent("distributeur").putO2AObject(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
		//need timer ? 
    }
	
}
