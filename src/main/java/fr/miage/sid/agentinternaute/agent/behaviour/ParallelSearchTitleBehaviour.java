package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Event;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 * Ex de doc : https://djug.developpez.com/java/jade/behaviours/
 */
public class ParallelSearchTitleBehaviour extends ParallelBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final long serialVersionUID = 4023562697885420376L;
	
	private static final Logger LOGGER = Logger.getLogger(ParallelSearchTitleBehaviour.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

//	private Event event;
//	
//	private DFAgentDescription[] distributors;
//	
//	private JSONArray response;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public ParallelSearchTitleBehaviour(Event event, DFAgentDescription[] distributors) {
		super(ParallelBehaviour.WHEN_ALL);
//		this.event = event;
//		this.distributors = distributors;
//		this.response = new JSONArray();
		
		JSONArray response = new JSONArray();
		
		LOGGER.info("===========================================================");
		LOGGER.info("Call the ParallelSearchTitleBehaviour.");
		
		for (DFAgentDescription distributor : distributors) {
//			this.addSubBehaviour(new HandleSearchTitleBehaviour(response, distributor));
			
			this.addSubBehaviour(new CyclicBehaviour() {
				private static final long serialVersionUID = 3671564596258417254L;

				@Override
				public void action() {
					// On récupère l'ACL message
					ACLMessage message = myAgent.receive();
					if (message != null) {
						LOGGER.info("-----------------------------------------------------------");
						LOGGER.info("You wait for a message from : " + distributor.getName());
						LOGGER.info("You receive a message from : " + message.getSender());
						// Logique métier : on veut un ACLMessage.INFORM et 
						if (message.getPerformative() == ACLMessage.INFORM && message.getSender().equals(distributor.getName())) {

							// On récupère le JSON
							String content = message.getContent();
							JSONObject JSON = new JSONObject(content);
							
							// On ajoute le nom du distributeur
							JSON.put("distributeur", message.getSender());
							response.put(JSON);
							this.done();
						}
					}
				}
			});
		}
		
		System.out.println("Tu es là : ParallelSearchTitleBehaviour !");
		System.out.println(response.toString());
		
		// Transmit to API
		event.notifyProcessed(response.toString());
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/

}
