package fr.miage.sid.agentinternaute.service;

import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import fr.miage.sid.agentinternaute.dto.RatingsDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import jade.util.Event;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalComService {

	private Object sendToAgent(Object object, String agentName, int timeout) {
		AgentController agentController;
		try {
			agentController = JadeAgentContainer.getInstance().getAgentContainer().getAgent(agentName);
			Event event = new Event(-1, this, object);
			agentController.putO2AObject(event, AgentController.ASYNC);
			return event.waitUntilProcessed(timeout * 1000);
		} catch (ControllerException | InterruptedException e) {
			System.err.println("Failed to send object to " + agentName + " : " + e.getMessage());
			return null;
		}
	}
	
	public Object sendSearchTitleToAgent(String request) {
		return sendToAgent(request, AgentTypes.AGENT_DISTRIBUTEUR, 15);
	}

	public Object sendRatingsToAgent(Profile profile, RatingsDTO r) {

		// Construct JSON message
		JSONObject message = new JSONObject();
		message.put("agent name", profile.getName());
		message.put("item type", r.getItemType());
		message.put("item id", r.getItemId());
		message.put("item rating", r.getMediumRating());
		message.put("distributor rating", r.getDistributorRating());
		message.put("productor rating", r.getProductorRating());
		if(r.getItemType().equals("music")) {
			JSONObject artists = new JSONObject();
			for(Entry<Integer, Double> entry : r.getArtistsRating().entrySet()) {
				artists.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("artist rating", artists);
		} else {
			JSONObject actors = new JSONObject();
			for(Entry<Integer, Double> entry : r.getActorsRating().entrySet()) {
				actors.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("actors rating", actors);
			
			JSONObject directors = new JSONObject();
			for(Entry<Integer, Double> entry : r.getDirectorsRating().entrySet()) {
				directors.put(Integer.toString(entry.getKey()), Double.toString(entry.getValue()));
			}
			message.put("directors rating", directors);
		}
		
		return sendToAgent(message.toString(), profile.getName(), 10);
	}
}
