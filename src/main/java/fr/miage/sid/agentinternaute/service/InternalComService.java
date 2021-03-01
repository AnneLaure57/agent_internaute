package fr.miage.sid.agentinternaute.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import fr.miage.sid.agentinternaute.agent.JadeAgentContainer;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import fr.miage.sid.agentinternaute.entity.Actor;
import fr.miage.sid.agentinternaute.entity.Artist;
import fr.miage.sid.agentinternaute.entity.Director;
import fr.miage.sid.agentinternaute.entity.Purchase;
import jade.util.Event;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalComService {

	/**
	 * Send a stringified json message to our own agents, using a jade event and O2A and, wait for agent response 
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
	
	public Object sendSearchTitleToAgent(String request) {
		
		
		return sendToAgent(1, request, AgentTypes.AGENT_DISTRIBUTEUR, 15);
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
		
		if(purchase.getItemType().equals("music")) {
			JSONObject artists = new JSONObject();
			for(Artist entry : purchase.getArtistes()) {
				artists.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("artist rating", artists);
		} else {
			JSONObject actors = new JSONObject();
			for(Actor entry : purchase.getActeurs()) {
				actors.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("actors rating", actors);
			
			JSONObject directors = new JSONObject();
			for(Director entry : purchase.getRealisateurs()) {
				directors.put(Integer.toString(entry.getId()), Double.toString(entry.getRating()));
			}
			message.put("directors rating", directors);
		}
		
		// Send it to the agent
		return sendToAgent(0, message.toString(), agentName, 10);
	}
}
