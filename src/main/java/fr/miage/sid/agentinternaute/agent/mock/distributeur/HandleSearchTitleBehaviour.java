
package fr.miage.sid.agentinternaute.agent.mock.distributeur;
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
public class HandleSearchTitleBehaviour extends CyclicBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/
	
	private static final long serialVersionUID = 1456892866260756940L;
	
	private static final Logger LOGGER = Logger.getLogger(HandleSearchTitleBehaviour.class.getName());
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public HandleSearchTitleBehaviour(AgentDistributeur agentDistributeur) {
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
					LOGGER.info("***********************************************************");
					LOGGER.info("Ce distributeur vous répond : " + myAgent.getName());
					
					// Création d'une Mock réponse
					JSONObject response;
					System.out.println("Name length : " + myAgent.getName().substring(myAgent.getName().length()-2));
					if(myAgent.getName().substring(myAgent.getName().length()-2).equals("1")) {
						response = JSONDistributeur1.searchTitleJSONresponse();
					} else {
						response = JSONDistributeur2.searchTitleJSONresponse();
					}
					
					// Reply
					AgentAndACLMessageUtils.replyMessage(myAgent, ACLMessage.INFORM, response.toString(), message);
				}
			}
		}
	}
}
