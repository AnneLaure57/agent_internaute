package fr.miage.sid.agentinternaute.agent;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.behaviours.RateBehaviour;
import fr.miage.sid.agentinternaute.agent.behaviours.SearchFiltersBehaviour;
import fr.miage.sid.agentinternaute.agent.behaviours.SearchTitleBehaviour;
import fr.miage.sid.agentinternaute.service.ProfileService;
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

public class AgentInternaute extends Agent {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final long serialVersionUID = -1271454263303780513L;

	private static final Logger LOGGER = Logger.getLogger(AgentInternaute.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

	private String name;
	// TODO : fix warnings
	@SuppressWarnings("unused")
	private ProfileService profileService;
	private String service = "internaute";
	// TODO : fix warnings
	@SuppressWarnings("unused")
	private AID AID = new AID();

    /* ========================================= Methodes ============================================== */ 
	
	// TODO : fix warnings
	@SuppressWarnings("serial")
	protected void setup() {
		// On récupère le nom de l'internaute
		Object[] args = getArguments();
		if (args.length > 0) {
			this.name = (String) args[0]; // Nom de l'internaute en paramètre de la ligne de commande
		} else {
			this.name = "Bob_" + UUID.randomUUID();
		}	
		
		// On s'enregistre auprès du DF
		this.registerService();
		
		// On accpte de communiquer
		setEnabledO2ACommunication(true, 0);
		
		// 300000 => 30 sec
		// 1000000 => 10 min
		Long timerTickerBehaviour = (long) 1000000;
		
		LOGGER.log(Level.INFO, "Bonjour. Bienvenue sur " + this.getLocalName());
		addBehaviour(new TickerBehaviour(this, timerTickerBehaviour) {
			protected void onTick() {
				/********** WITHOUT BEHAVIOUR *****/
				long tStart = System.currentTimeMillis();
				System.out.println("Coucou, je suis up");
				PassingTime.checkDate(tStart);
				
			}
		} );
		
		addBehaviour(new RateBehaviour(this));
		addBehaviour(new SearchTitleBehaviour(this));
		addBehaviour(new SearchFiltersBehaviour(this));
	}

	/*
	 * Recherche d'un type d'agent
	 */
	private DFAgentDescription[] searchAgents(String serviceName) {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceName);
		dfd.addServices(sd);

		try {
			return DFService.search(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Enregistrement auprès du Directory Facilitator
	 */
	private void registerService() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType(this.service);
		sd.setName(this.name);

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
			doDelete();
		}
	}
	
	/*
	 * Recherche d'un agent e-reputation
	 */
	public DFAgentDescription getAgentReputation() {
		DFAgentDescription[] results = searchAgents("reputation");
		if (results != null && results.length > 0) {
			return results[0];
		}
		return null;
	}

	/*
	 * Recherche des agents distributeurs
	 */
	public DFAgentDescription[] getAgentsDistributeurs() {
		DFAgentDescription[] results = searchAgents("distributeur");
		if (results != null && results.length > 0) {
			return results;
		}
		return null;
	}
	
	/*
	 *  Rechercher une oeuvre ???
	 */

	/*
	 * Envoi préférences profil, type, titre
	 */
	// TODO : fix warnings
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
			// Printout a dismissal message
			System.out.println("l'agent " +getAID().getName()+ " s'est arrêté.");
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}	
}
