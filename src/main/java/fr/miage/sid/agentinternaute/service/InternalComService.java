package fr.miage.sid.agentinternaute.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.dto.RatingsDTO;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.entity.Purchase;
import jade.util.Event;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalComService {

	private String sendToAgent(int eventType, String jsonString, String agentName, int timeout) {
		AgentController agentController;
		try {
			agentController = JadeAgentContainer.getInstance().getAgentContainer().getAgent(agentName);
			System.out.println("-----------------------------------> " + agentController.getName());
			Event event = new Event(eventType, this, jsonString);
			agentController.putO2AObject(event, AgentController.ASYNC);
			return (String) event.waitUntilProcessed(timeout * 1000);
		} catch (ControllerException | InterruptedException e) {
			System.err.println("Failed to send object to " + agentName + " : " + e.getMessage());
			return null;
		}
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
