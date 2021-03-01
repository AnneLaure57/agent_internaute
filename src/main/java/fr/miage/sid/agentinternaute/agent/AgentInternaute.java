package fr.miage.sid.agentinternaute.agent;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.behaviours.InternalComBehaviour;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import fr.miage.sid.agentinternaute.agent.commons.PassingTime;
import fr.miage.sid.agentinternaute.service.ProfileService;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import net.minidev.json.JSONObject;

public class AgentInternaute extends Agent {

	private static final long serialVersionUID = -1271454263303780513L;

	private static final Logger LOGGER = Logger.getLogger(AgentInternaute.class.getName());

	private String name;
	// TODO : fix it
	@SuppressWarnings("unused")
	private ProfileService profileService;
	private String service = AgentTypes.AGENT_INTERNAUTE;
	private AID AID = new AID();
	
	/**
	 * Setup method of agent
	 */
	protected void setup() {
		// On s'assure que notre Agent a bien un ID
		if (this.AID == null) {
			this.AID = new AID();
		}
		
		// On récupère le nom de l'internaute
		Object[] args = getArguments();
		if (args.length > 0) {
			this.name = (String) args[0]; // Nom de l'internaute en paramètre de la ligne de commande
		} else {
			this.name = "Bob_" + UUID.randomUUID();
		}	
		
		// On s'enregistre auprès du DF
		this.registerService();
		LOGGER.log(Level.INFO, "Bonjour. Bienvenue sur " + this.getLocalName());
		
		// Activation de O2A pour la communication avec le reste de l'application
		setEnabledO2ACommunication(true, 0);
		Behaviour internalCommunication = new InternalComBehaviour(this);
		setO2AManager(internalCommunication);
		addBehaviour(internalCommunication);
		
		// 300000 => 30 sec
		// 1000000 => 10 min
		Long timerTickerBehaviour = (long) 1000000;
		
		addBehaviour(new TickerBehaviour(this, timerTickerBehaviour) {
			private static final long serialVersionUID = 9192646164357857629L;

			protected void onTick() {
				/********** WITHOUT BEHAVIOUR *****/
				long tStart = System.currentTimeMillis();
				System.out.println("Coucou, je suis up");
				PassingTime.checkDate(tStart);				
			}
		});
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
		DFAgentDescription[] results = searchAgents(AgentTypes.AGENT_E_REPUTATION);
		if (results != null && results.length > 0) {
			return results[0];
		}
		return null;
	}

	/*
	 * Recherche des agents distributeurs
	 */
	public DFAgentDescription[] getAgentsDistributeurs() {
		DFAgentDescription[] results = searchAgents(AgentTypes.AGENT_DISTRIBUTEUR);
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
	private void sendSearchInformations(JSONObject messageJSON, AID ID) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(ID);
			
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
			System.out.println("L'agent " + getAID().getName() + " s'est arrêté.");
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}	
}
