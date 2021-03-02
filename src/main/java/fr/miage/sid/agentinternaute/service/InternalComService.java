package fr.miage.sid.agentinternaute.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.entity.Actor;
import fr.miage.sid.agentinternaute.entity.Artist;
import fr.miage.sid.agentinternaute.entity.Director;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import jade.util.Event;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalComService {

	private final static String MOVIES = "movies", MUSICS = "musics", TV_SHOWS = "tv_shows";

	/**
	 * Send a stringified json message to our own agents, using a jade event and O2A
	 * and, wait for agent response
	 * 
	 * @param eventType
	 * @param jsonString
	 * @param agentName
	 * @param timeout
	 * @return
	 */
	private String sendToAgent(int eventType, String jsonString, String agentName, int timeout) {
		AgentController agentController;
		try {
			agentController = JadeAgentContainer.getInstance().getAgentContainer().getAgent(agentName);
			Event event = new Event(eventType, this, jsonString);
			agentController.putO2AObject(event, AgentController.ASYNC);
			return (String) event.waitUntilProcessed(timeout * 1000);
		} catch (ControllerException | InterruptedException e) {
			System.err.println("Failed to send object to " + agentName + " : " + e.getMessage());
			return null;
		}
	}

	public String sendSearchTitleToAgent(String title, Boolean movies, Boolean musics, Boolean tv_shows, Profile profile) {

		// Construct JSON message
		JSONObject searchMessage = new JSONObject();
		
		// we need it to say what we want	
		searchMessage.put("request", ACLMessageTypes.REQUEST_SEARCH_TITLE.getValue());

		// what we search
		searchMessage.put("title", title);

		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (movies)
			checkTypes.add(MOVIES);
		if (musics)
			checkTypes.add(MUSICS);
		if (tv_shows)
			checkTypes.add(TV_SHOWS);
		searchMessage.put("types", Arrays.toString(checkTypes.toArray()));

		// send our profile
		if (profile != null) {
			JSONObject userProfile = new JSONObject();

			userProfile.put("name", profile.getName());
			userProfile.put("age", Integer.toString(profile.getAge()));
			userProfile.put("sex", profile.getSex());

			userProfile.put("prefered_actors", profile.getPreferedActors().toString());
			userProfile.put("prefered_directors", profile.getPreferedDirectors().toString());
			userProfile.put("prefered_musics_artists", profile.getPreferedMusicArtists().toString());
			userProfile.put("prefered_musics_genres", profile.getPreferedMusicGenres().toString());
			userProfile.put("prefered_videos_genres", profile.getPreferedVideoGenres().toString());

			searchMessage.put("user_profile", userProfile);
		}

		System.out.println(searchMessage.toString());
		String agentName = profile.getName();
		
		String temp = sendToAgent(1, searchMessage.toString(), agentName, 15);
		System.out.println("Tu es là : InternalComService !");
		System.out.println(temp);
		return temp;
	}

	public String sendRatingsToAgent(String agentName, Purchase purchase) {

		// Construct JSON message
		JSONObject message = new JSONObject();
		message.put("agent name", agentName);

		JSONObject media = new JSONObject();
		media.put(Integer.toString(purchase.getItemId()), Double.toString(purchase.getItemRating()));
		message.put("media", media);

		JSONObject distributor = new JSONObject();
		distributor.put(purchase.getProducteurId(), Double.toString(purchase.getProducteurRating()));
		message.put("distributor", distributor);

		JSONObject productor = new JSONObject();
		productor.put(purchase.getDistributeurId(), Double.toString(purchase.getDistributeurRating()));
		message.put("productor", productor);

		if (purchase.getItemType().equals("music")) {
			JSONObject artists = new JSONObject();
			for (Artist entry : purchase.getArtistes()) {
				artists.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("artist rating", artists);
		} else {
			JSONObject actors = new JSONObject();
			for (Actor entry : purchase.getActeurs()) {
				actors.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("actors rating", actors);

			JSONObject directors = new JSONObject();
			for (Director entry : purchase.getRealisateurs()) {
				directors.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("directors rating", directors);
		}

		// Send it to the agent
		return sendToAgent(0, message.toString(), agentName, 10);
	}

	public String sendAcceptProposalToAgent(String agentName, PurchaseDTO p) {
		// Construct JSON message
		JSONObject message = new JSONObject();
		// TODO

		// Send it to the agent
		return sendToAgent(3, message.toString(), agentName, 10);
	}
}
