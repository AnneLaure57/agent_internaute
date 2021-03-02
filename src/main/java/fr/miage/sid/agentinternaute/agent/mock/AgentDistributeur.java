package fr.miage.sid.agentinternaute.agent.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public class AgentDistributeur extends Agent {
	
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final long serialVersionUID = -1271454263303780513L;

	private static final Logger LOGGER = Logger.getLogger(AgentDistributeur.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private String name;
	private String service = AgentTypes.AGENT_DISTRIBUTEUR.getValue();
	private AID AID = new AID();

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/
	
	/**
	 * Method setup : to register a Distributeur Agent (set a random name, cyclic behaviour and register to Jade service). 
	 */
	protected void setup() {
		// On s'assure que notre Agent a bien un ID
		if (this.AID == null) {
			this.AID = new AID();
		}
		
		// On renseigne un nom de distributeur (random)
		this.name = AgentTypes.AGENT_DISTRIBUTEUR + "_" + UUID.randomUUID();
		
		// On l'enregistre auprès du service Jade
		this.registerService();
		LOGGER.info("Bonjour Distributeur. Vous êtes enregistré en tant que : " + this.getLocalName());
		
		// On accpte de communiquer
		setEnabledO2ACommunication(true, 0);

		addBehaviour(new CyclicBehaviour() {
			private static final long serialVersionUID = 1456892866260756940L;

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
						
						if (JSON.get("resquest") ==  ACLMessageTypes.REQUEST_SEARCH_TITLE.getValue()) {
							
							LOGGER.info("************************************************************");
							LOGGER.info("************************************************************");
							LOGGER.info("************************************************************");
							
							Map<String, String> responsehMap = new HashMap<String, String>();
							ArrayList<String> oeuvres = new ArrayList<String>();
							oeuvres.add("Titi");
							oeuvres.add("Tata");
							oeuvres.add("Toto");
							responsehMap.put("types", Arrays.toString(oeuvres.toArray()));
							JSONObject response = new JSONObject(responsehMap);
							
							DFAgentDescription internaute = getAgentInternaute();
							sendMessage(response.toString(), internaute.getName());
						}
					}
				}
			}
		});
	}

	/**
	 * Method searchAgents : to search an other agent by name.
	 * 
	 * @param serviceName The name of the other agent to search.
	 * @return Return an array of DFAgentDescription. 
	 */
	private DFAgentDescription[] searchAgents(String serviceName) {
		// On créé le portrait robot de l'objet Jade que l'on cherche 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceName);
		dfd.addServices(sd);
		
		// On essaie de récupérer les agents qui match auprès du Directory Facilitator
		try {
			return DFService.search(this, dfd);
		} catch (FIPAException e) {
			LOGGER.severe("can't search the agent : " + serviceName);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method registerService : to regiser our Distributeur agent to the Directory Facilitator.
	 */
	private void registerService() {
		// On créé notre agent profile
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType(this.service);
		sd.setName(this.name);

		// On essaie de s'enregistrer
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			LOGGER.severe(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
			doDelete();
		}
	}
	
	/**
	 * Method getAgentInternaute : to find the agent with type "internaute".
	 * 
	 * @return Return the agent with type "internaute", if it was find, otherwise return null.
	 */
	public DFAgentDescription getAgentInternaute() {
		DFAgentDescription[] results = searchAgents(AgentTypes.AGENT_INTERNAUTE.getValue());
		if (results != null && results.length > 0) {
			return results[0];
		}
		return null;
	}
	
	/**
	 * Method sendMessage : to send a JSON message (into a Java String) to a specific agent (find by it's ID).
	 * 
	 * @param message JSON message (into a Java String) to send.
	 * @param ID The ID of the agent who will receive the message.
	 */
	private void sendMessage(String message, AID ID) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(ID);

			aclMessage.setContent(message);

			super.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Method takeDown : to shut down the Agent Distributeur.
	 */
	protected void takeDown() {
		try {
			LOGGER.info("L'agent " +getAID().getName()+ " s'est arrêté.");
			DFService.deregister(this);
		} catch (FIPAException fe) {
			LOGGER.severe("Error during Agent Distributeur takeDown");
			fe.printStackTrace();
		}
	}	

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}
