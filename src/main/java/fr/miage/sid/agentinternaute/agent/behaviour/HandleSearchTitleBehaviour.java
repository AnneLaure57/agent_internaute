package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class HandleSearchTitleBehaviour extends CyclicBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/
	
	private static final long serialVersionUID = 1456892866260756940L;
	
	private static final Logger LOGGER = Logger.getLogger(HandleSearchTitleBehaviour.class.getName());
	
	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private JSONArray response;
	
	private DFAgentDescription distributor;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public HandleSearchTitleBehaviour(JSONArray response, DFAgentDescription distributor) {
		super();
		this.response = response;
		this.distributor = distributor;
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

//	@Override
//	public void action() {
//		// On récupère l'ACL message
//		ACLMessage message = myAgent.receive();
//		if (message != null) {
//			// On récupère l
//			String content = message.getContent();
//			JSONObject JSON = new JSONObject(content);
//			
//			// Logique métier
//			if (message.getPerformative() == ACLMessage.INFORM) {
//				JSON.put("distributeur")
//				
//				// Transmit to API
//				event.notifyProcessed(content);
//			}
//		}
//	}
	
	@Override
	public void action() {
		// On récupère l'ACL message
		ACLMessage message = myAgent.receive();
		if (message != null) {
			LOGGER.info("-----------------------------------------------------------");
			LOGGER.info("You wait for a message from : " + this.distributor.getName());
			LOGGER.info("You receive a message from : " + message.getSender());
			// Logique métier : on veut un ACLMessage.INFORM et 
			if (message.getPerformative() == ACLMessage.INFORM && message.getSender().equals(this.distributor.getName())) {

				// On récupère le JSON
				String content = message.getContent();
				JSONObject JSON = new JSONObject(content);
				
				// On ajoute le nom du distributeur
				JSON.put("distributeur", message.getSender());
				this.response.put(JSON);
				this.done();
			}
		}
	}
}
