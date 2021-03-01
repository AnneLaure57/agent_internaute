package fr.miage.sid.agentinternaute.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
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
		
		String agentName = profile.getName();
		// Construct JSON message
		JSONObject searchMessage = new JSONObject();

		// what we search
		searchMessage.put("title", title);

		// tv_shows, musics etc.
		ArrayList<String> checkTypes = new ArrayList<String>();
		if (movies) checkTypes.add(MOVIES);
		if (musics)	checkTypes.add(MUSICS);
		if (tv_shows) checkTypes.add(TV_SHOWS);
		searchMessage.put("types", Arrays.toString(checkTypes.toArray()));
		
		// send our profile
		if(profile != null) {
			JSONObject userProfile = new JSONObject();
			
			userProfile.put("name", profile.getName());
			userProfile.put("age", Integer.toString(profile.getAge()));
			userProfile.put("sex", profile.getSex());
			
			userProfile.put("prefered_actors", profile.getPreferedActors().toString());
			userProfile.put("prefered_directors", profile.getPreferedDirectors().toString());
			userProfile.put("prefered_musics_artists", profile.getPreferedMusicArtists().toString());
			userProfile.put("prefered_musics_genres", profile.getPreferedMusicGenres().toString());
			userProfile.put("prefered_videos_genres", profile.getPreferedVideoGenres().toString());
			
			// Get preferences list => actors
			/*JSONObject actors = new JSONObject();
			for(Integer id : profile.getPreferedActors()) {
				actors.put("actor_id",id);
			}
			userProfile.put("prefered_actors", actors);
			// Get preferences list => directors
			JSONObject directors = new JSONObject();
			for(Integer id : profile.getPreferedDirectors()) {
				actors.put("director_id",id);
			}
			userProfile.put("prefered_directors", directors);
			// Get preferences list => musics
			JSONObject musicsArtists = new JSONObject();
			for(Integer id : profile.getPreferedMusicArtists()) {
				musicsArtists.put("music_artist_id",id);
			}
			userProfile.put("prefered_musics_artists", musicsArtists);
			// Get preferences list => musicGenres
			JSONObject musicsGenre = new JSONObject();
			for(Integer id : profile.getPreferedMusicGenres()) {
				musicsGenre.put("music_genre_id",id);
			}
			userProfile.put("prefered_musics_genres", musicsGenre);
			// Get preferences list => musicGenres
			JSONObject videosGenre = new JSONObject();
			for(Integer id : profile.getPreferedVideoGenres()) {
				videosGenre.put("video_genre_id",id);
			}
			userProfile.put("prefered_videos_genres", videosGenre);*/

			searchMessage.put("user_profile", userProfile);
		}
		
		return sendToAgent(1, searchMessage.toString(), agentName, 15);
	}

	public String sendRatingsToAgent(String agentName, Purchase purchase) {

		// Construct JSON message
		JSONObject message = new JSONObject();
		message.put("agent name", agentName);
		message.put("item type", purchase.getItemType());
		message.put("item id", purchase.getItemId());
		message.put("item rating", purchase.getMediumRating());
		message.put("distributor rating", purchase.getDistributorRating());
		message.put("productor rating", purchase.getProductorRating());
		if(purchase.getItemType().equals("music")) {
			JSONObject artists = new JSONObject();
			for(Entry<Integer, Double> entry : purchase.getArtistsRating().entrySet()) {
				artists.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("artist rating", artists);
		} else {
			JSONObject actors = new JSONObject();
			for(Entry<Integer, Double> entry : purchase.getActorsRating().entrySet()) {
				actors.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("actors rating", actors);
			
			JSONObject directors = new JSONObject();
			for(Entry<Integer, Double> entry : purchase.getDirectorsRating().entrySet()) {
				directors.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("directors rating", directors);
		}
		
		return sendToAgent(0, message.toString(), agentName, 10);
	}
}
