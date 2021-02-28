package fr.miage.sid.agentinternaute.agent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.behaviours.RateBehaviour;
import fr.miage.sid.agentinternaute.agent.behaviours.SearchFiltersBehaviour;
import fr.miage.sid.agentinternaute.agent.behaviours.SearchTitleBehaviour;
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
	private AID aid = new AID();

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
		
		// 300000 => 30 sec
		// 1000000 => 10 min
		Long timerTickerBehaviour = (long) 1000000;
		
		LOGGER.log(Level.INFO, "Bonjour. Bienvenue sur " + this.getLocalName());
		addBehaviour(new TickerBehaviour(this, timerTickerBehaviour) {
			protected void onTick() {
				/********** WITHOUT BEHAVIOUR *****/
				long tStart = System.currentTimeMillis();
				System.out.println("Coucou, je suis up");
				checkDate(tStart);
				
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
	 * Vérifier la date 
	 */
// 		1 min -> 1 j
////	30 min -> 1 mois
////	1h30 -> 3 mois
////	3h -> 6 mois
////	6h -> 12 mois
////	24h -> 4 ans
	// TODO : fix warnings
	@SuppressWarnings("unused")
	private void checkDate(long tStart) {
		
		//Set date format
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");
		//Convert Date + Timer
		Date currentDate = new Date();
		Date date2 =new Date(tStart);
		
		String currentDateTimeString = dateFormat.format(currentDate);
		String currentDateTimer = dateFormat.format(date2);
		
		System.out.println(" Date courante : " + currentDateTimeString +", timer : " + currentDateTimer);	
		
		//check diff between dates
		int diff = currentDate.compareTo(date2);
		Long newDiff = null;
		
//		 24h -> 4 ans
		if (diff == TimeUnit.HOURS.toMillis(24)) {
			newDiff = TimeUnit.DAYS.toMillis(4 * 365);
//		6h -> 12 mois
		} else if (diff == TimeUnit.HOURS.toMillis(6)) {
			newDiff = TimeUnit.DAYS.toMillis(12 * 30);
//		3h -> 6 mois
		} else if (diff == TimeUnit.HOURS.toMillis(3)) {
			newDiff = TimeUnit.DAYS.toMillis(6 * 30);
//		 1h30 -> 3 mois
		} else if (diff == TimeUnit.MINUTES.toMillis(90)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//		30 min -> 1 mois
		} else if (diff == TimeUnit.MINUTES.toMillis(30)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//	 		1 min -> 1 j		
		} else if (diff == TimeUnit.MINUTES.toMillis(1)) {
			newDiff = TimeUnit.DAYS.toMillis(1);
		} else {
			System.out.println(currentDate + " is equal to " + date2);
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
