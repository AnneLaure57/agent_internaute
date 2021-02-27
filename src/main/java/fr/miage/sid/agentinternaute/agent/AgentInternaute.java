package fr.miage.sid.agentinternaute.agent;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.service.ProfileService;
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
	
	private static final Logger LOGGER = Logger.getLogger(AgentInternaute.class.getName());

	private String name;
	private ProfileService profileService;
	private String service = "internaute";
	private AID aid = new AID();

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
		
		addBehaviour(new TickerBehaviour(this, 60000) {
			protected void onTick() {
				/********** WITHOUT BEHAVIOUR *****/
				System.out.println("Coucou, je suis up");
			}
		} );
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
	 * Envoi d'un message, à former en JSON et à envoyer en String
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
	 *  Rechercher une oeuvre ???
	 */

	/*
	 * Envoi préférences profil, type, titre
	 */
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
	 * Envoi message pour la recherche
	 * 
	 * param : Agent Client, String message -> contenu du message, AID receiver et le mot clé recherche
	 */
	private void sendMessageResearch(Agent myAgent, String mess, AID id, String recherche) {
		// TODO
	}

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
	
	/*
	 * Préparer achat oeuvre
	 */
	private void checkOrder(ACLMessage messageClient) {
		
	}
	
	
	/*
	 * Attendre Réponse Recherche
	 */
	private void getResponseSearch() {
		
	}
	
	/*
	 * Recevoir la liste des médias proposés par les distributeurs
	 * Faire intervenir les behaviours dedans
	 */
	private void getListMedias() {
		//TODO AL : put here switch case with different behaviour
	}
	
	/*
	 * Envoyer la commande (accept_proposal)
	 * 
	 */
	private void sendOrder(ACLMessage messageClient) {
		
	}
	
	/*
	 * Prix commande/média
	 * 
	 */
	private void getPriceOrder() {
		
	}
	
	/*
	 * Prix commande/média
	 * 
	 */
	private void buyMedia() {
		
	}
	
}
