package fr.miage.sid.agentinternaute.mock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
	private AID aid = new AID();

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
				System.out.println("Coucou, je suis up");
				checkDate(tStart);
				
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

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceName);
		dfd.addServices(sd);

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
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		ServiceDescription sd = new ServiceDescription();
		sd.setType(this.service);
		sd.setName(this.name);

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			LOGGER.severe(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
			doDelete();
		}
	}
	
	/*
	 * Recherche d'un agent internaute
	 */
	public DFAgentDescription getAgentInternaute() {
		DFAgentDescription[] results = searchAgents("internaute");
		if (results != null && results.length > 0) {
			return results[0];
		}
		return null;
	}

	/*
	 * Envoi d'un message, à former en JSON et à envoyer en String
	 */
	// TODO
	@SuppressWarnings("unused")
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

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}
