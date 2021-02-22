package fr.miage.sid.agentinternaute.agent;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.service.ProfileService;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AgentInternaute extends Agent {
	
	private static final Logger LOGGER = Logger.getLogger(AgentInternaute.class.getName());

	private String name;
	private ProfileService profileService;
	private String service = "internaute";
	private AID aid = new AID();

	@Override
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

		LOGGER.log(Level.INFO, "Bonjour. Bienvenue sur " + this.getLocalName());
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
	 * Envoi d'un message, à passer sous forme de JSON ???
	 */
	private void sendMessage(String mess, AID id) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(id);

			aclMessage.setContent(mess);

			super.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Déférérencement dans l'annuaire
	 */
//	protected void takeDown() {
//		try {
//			DFService.deregister(this);
//		} catch (FIPAException fe) {
//			fe.printStackTrace();
//		}
//	}	
	
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
}
