package fr.miage.sid.agentinternaute.agent.mock.distributeur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public class SearchTitleBehaviour extends CyclicBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/
	
	private static final long serialVersionUID = 1456892866260756940L;
	
	private static final Logger LOGGER = Logger.getLogger(SearchTitleBehaviour.class.getName());
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public SearchTitleBehaviour(AgentDistributeur agentDistributeur) {
		super(agentDistributeur);
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/
	
	@Override
	public void action() {
		// On récupère l'ACL message
		ACLMessage message = myAgent.receive();
		if (message != null) {
			// On récupère le JSON
			String content = message.getContent();
			JSONObject JSON = new JSONObject(content);
			
			// Logique métier
			if (message.getPerformative() == ACLMessage.REQUEST) {
				// On vérifie que l'on a ce q'il nous faut
				if (! JSON.has("request")) {
					LOGGER.severe("It missing the main key : 'request'.");
				}
				
				if (JSON.getString("request").equals(ACLMessageTypes.REQUEST_SEARCH_TITLE.getValue())) {
					// Création d'une Mock réponse
					Map<String, String> responsehMap = new HashMap<String, String>();
					ArrayList<String> oeuvres = new ArrayList<String>();
					oeuvres.add("Titi");
					oeuvres.add("Tata");
					oeuvres.add("Toto");
					responsehMap.put("types", Arrays.toString(oeuvres.toArray()));
					
					// JSON response to String 
					JSONObject response = new JSONObject(responsehMap);
					
					// Reply
					AgentAndACLMessageUtils.replyMessage(myAgent, ACLMessage.INFORM, response.toString(), message);
				}
			}
		}
	}
}
