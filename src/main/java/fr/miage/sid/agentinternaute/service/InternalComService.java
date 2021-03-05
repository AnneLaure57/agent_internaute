package fr.miage.sid.agentinternaute.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.dto.PurchaseDTO;
import fr.miage.sid.agentinternaute.dto.SearchDTO;
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
	 */
	private Object sendToAgent(int eventType, String jsonString, String agentName, int timeout) {
		AgentController agentController;
		try {
			agentController = JadeAgentContainer.getInstance().getAgentContainer().getAgent(agentName);
			Event event = new Event(eventType, this, jsonString);
			agentController.putO2AObject(event, AgentController.ASYNC);
			return event.waitUntilProcessed(timeout * 1000);
		} catch (ControllerException | InterruptedException e) {
			System.err.println("Failed to send object to " + agentName + " : " + e.getMessage());
			return null;
		}
	}

	private JSONObject getJSONPreferences(Profile profile) {
		JSONObject userProfile = new JSONObject();

		userProfile.put("name", profile.getName());
		userProfile.put("age", Integer.toString(profile.getAge()));
		userProfile.put("sex", profile.getSex());

		userProfile.put("prefered_actors", profile.getPreferedActors().toString());
		userProfile.put("prefered_directors", profile.getPreferedDirectors().toString());
		userProfile.put("prefered_musics_artists", profile.getPreferedMusicArtists().toString());
		userProfile.put("prefered_musics_genres", profile.getPreferedMusicGenres().toString());
		userProfile.put("prefered_videos_genres", profile.getPreferedVideoGenres().toString());

		return userProfile;
	}

	public JSONObject sendSearchTitleToAgent(SearchDTO searchParam, Profile profile) {

		// Construct JSON message
		JSONObject searchMessage = new JSONObject();

		// we need it to say what we want
		searchMessage.put("request", ACLMessageTypes.REQUEST_SEARCH_TITLE.getValue());

		// what we search
		searchMessage.put("title", searchParam.getSearchField());

		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (searchParam.getMovies())
			checkTypes.add(MOVIES);
		if (searchParam.getMusics())
			checkTypes.add(MUSICS);
		if (searchParam.getTvShows())
			checkTypes.add(TV_SHOWS);
		searchMessage.put("types", Arrays.toString(checkTypes.toArray()));

		// send our profile
		if (profile != null) {
			JSONObject userProfile = getJSONPreferences(profile);
			searchMessage.put("user_profile", userProfile);
		}

		String agentName = profile.getName();

		System.out.println("InternalComService -> envoi de la requête à l'agent internaute " + agentName + " : "
				+ searchMessage.toString());

		return (JSONObject) sendToAgent(1, searchMessage.toString(), agentName, 10);
	}

	public JSONObject sendSearchFiltersToAgent(SearchDTO searchParam, Profile profile) {
		// Construct JSON message
		JSONObject searchMessage = new JSONObject();

		// we need it to say what we want
		searchMessage.put("request", ACLMessageTypes.REQUEST_SEARCH_FILTER.getValue());

		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (searchParam.getMovies())
			checkTypes.add(MOVIES);
		if (searchParam.getMusics())
			checkTypes.add(MUSICS);
		if (searchParam.getTvShows())
			checkTypes.add(TV_SHOWS);
		searchMessage.put("types", Arrays.toString(checkTypes.toArray()));
		
		// filters
		searchMessage.put("selected_video_genres", searchParam.getSelectedVideoGenres());
		searchMessage.put("selected_music_genres", searchParam.getMusics());
		searchMessage.put("selected_artists", searchParam.getSelectedArtists());
		searchMessage.put("selected_actors", searchParam.getSelectedActors());
		searchMessage.put("selected_directors", searchParam.getSelectedDirectors());

		// send our profile
		if (profile != null) {
			JSONObject userProfile = getJSONPreferences(profile);
			searchMessage.put("user_profile", userProfile);
		}

		String agentName = profile.getName();

		System.out.println("InternalComService -> envoi de la requête à l'agent internaute " + agentName + " : "
				+ searchMessage.toString());

		return (JSONObject) sendToAgent(2, searchMessage.toString(), agentName, 10);
	}

	public JSONObject sendRatingsToAgent(String agentName, Purchase purchase) {

		// Construct JSON message
		JSONObject message = new JSONObject();
		message.put("agent", agentName);

		JSONObject media = new JSONObject();
		media.put(Integer.toString(purchase.getItemId()), Double.toString(purchase.getItemRating()));
		message.put("oeuvre", media);

		JSONObject distributor = new JSONObject();
		distributor.put("PRODUCTEUR1", "2.5");
		message.put("distributeurs", distributor);

		JSONObject productor = new JSONObject();
		productor.put(purchase.getDistributeurId(), Double.toString(purchase.getDistributeurRating()));
		message.put("producteurs", productor);

		if (purchase.getItemType() != null && purchase.getItemType().equals("music")) {
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
			message.put("acteurs", actors);

			JSONObject directors = new JSONObject();
			for (Director entry : purchase.getRealisateurs()) {
				directors.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("realisateurs", directors);
		}

		System.out.println("InternalComService -> envoi de la requête à l'agent internaute " + agentName + " : "
				+ message.toString());

		// Send it to the agent
		return (JSONObject) sendToAgent(0, message.toString(), agentName, 10);
	}

	public JSONObject sendAcceptProposalToAgent(String agentName, PurchaseDTO p) {

		// Construct JSON message
		JSONObject message = new JSONObject();
		message.put("distributor", p.getDistributeur());
		message.put("id", p.getId());
		message.put("prix", p.getPrix());

		// Send it to the agent
		return (JSONObject) sendToAgent(3, message.toString(), agentName, 10);
	}
}
