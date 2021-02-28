package fr.miage.sid.agentinternaute.mock;

import java.util.UUID;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.commons.PassingTime;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import net.minidev.json.JSONObject;

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
	private String service = "distributeur";
	private AID AID = new AID();

	/* ========================================= Constructeurs ========================================= */ /*=========================================*/
	
    /* ========================================= Methodes ============================================== */ /*=========================================*/
	
	/**
	 * Method setup : to register a Distributeur Agent (set a random name, cyclic behaviour and register to Jade service). 
	 */
	protected void setup() {
		// On renseigne un nom de distributeur (random)
		this.name = "Distributeur_" + UUID.randomUUID();
		
		// On l'enregistre auprès du service Jade
		this.registerService();
		LOGGER.info("Bonjour Distributeur. Vous êtes enregistré en tant que : " + this.getLocalName());
		
		// Ajout d'un behaviour cyclique pour pas que l'agetn soit takeDown instantanément
		Long timerTickerBehaviour = (long) 1000000;
		addBehaviour(new TickerBehaviour(this, timerTickerBehaviour) {
			private static final long serialVersionUID = -4616758656969662837L;

			protected void onTick() {
				/********** WITHOUT BEHAVIOUR *****/
				long tStart = System.currentTimeMillis();
				System.out.println("Hello, je suis up");
				PassingTime.checkDate(tStart);
				
			}
		} );
	
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
		DFAgentDescription[] results = searchAgents("internaute");
		if (results != null && results.length > 0) {
			return results[0];
		}
		return null;
	}
	
	/**
	 * Method sendMessage : to send a JSON message (into a Java String) to a specific agent (find by it's ID).
	 * 
	 * @param message JSON message (into a Java String) to send.
	 * @param id The ID of the agent who will receive the message.
	 */
	private void sendMessage(String message, AID id) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(id);

			aclMessage.setContent(message);

			super.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 *  Rechercher une oeuvre ???
	 */

	/*
	 * Envoi préférences profil, type, titre
	 */
	// TODO
	@SuppressWarnings("unused")
	private void sendSearchInformations(JSONObject messageJSON, AID id) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(id);
			
			// convert JSON --> String
			String message = messageJSON.toString();
			
			aclMessage.setContent(message);
			super.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	/*
	/*
	 * Déférérencement dans l'annuaire
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
