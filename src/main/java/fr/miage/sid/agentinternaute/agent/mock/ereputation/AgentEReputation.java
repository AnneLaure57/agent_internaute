package fr.miage.sid.agentinternaute.agent.mock.ereputation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.ACLMessageTypes;
import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
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
public class AgentEReputation extends Agent {
	
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final long serialVersionUID = -1271454263303780513L;

	private static final Logger LOGGER = Logger.getLogger(AgentEReputation.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private String name;
	private String service = AgentTypes.AGENT_E_REPUTATION.getValue();
	private AID AID = new AID();

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/
	
	/**
	 * Method setup : to register a E réputation Agent (set a random name, cyclic behaviour and register to Jade service). 
	 */
	protected void setup() {
		// On s'assure que notre Agent a bien un ID
		if (this.AID == null) {
			this.AID = new AID();
		}
		
		// On renseigne un nom de distributeur (random)
		this.name = AgentTypes.AGENT_E_REPUTATION + "_" + UUID.randomUUID();
		
		// On l'enregistre auprès du service Jade
		this.registerService();
		LOGGER.info("Bonjour E Réputation. Vous êtes enregistré en tant que : " + this.getLocalName());
		
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
					if (message.getPerformative() == ACLMessage.INFORM) {
						// On vérifie que l'on a ce q'il nous faut
						if (! JSON.has("request")) {
							LOGGER.severe("It missing the main key : 'request'.");
						}
						
						if (JSON.get("resquest") ==  ACLMessageTypes.REQUEST_SEARCH_TITLE.getValue()) {
							Map<String, String> responsehMap = new HashMap<String, String>();
							ArrayList<String> oeuvres = new ArrayList<String>();
							oeuvres.add("Titi");
							oeuvres.add("Tata");
							oeuvres.add("Toto");
							responsehMap.put("types", Arrays.toString(oeuvres.toArray()));
							JSONObject response = new JSONObject(responsehMap);
							
//							DFAgentDescription internaute = getAgentInternaute();
							AID senderAID = message.getSender();
							AgentAndACLMessageUtils.sendMessage(myAgent, ACLMessage.AGREE, response.toString(), senderAID);
						}
					}
				}
			}
		});
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
			LOGGER.severe(getLocalName() + " registration with DF unsucceeded. Reason : " + e.getMessage());
			doDelete();
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
