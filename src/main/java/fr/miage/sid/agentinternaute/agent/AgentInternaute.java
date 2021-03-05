package fr.miage.sid.agentinternaute.agent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.miage.sid.agentinternaute.agent.behaviour.InternalComBehaviour;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import fr.miage.sid.agentinternaute.agent.commons.PassingTime;
import fr.miage.sid.agentinternaute.agent.commons.Satisfaction;
import fr.miage.sid.agentinternaute.entity.Profile;
import fr.miage.sid.agentinternaute.service.ProfileService;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgentInternaute extends Agent {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AgentInternaute.class.getName());

	private String name;

	private ProfileService profileService;
	private String service = AgentTypes.AGENT_INTERNAUTE.getValue();
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
		
		/********** CHECK SATISFACTION *****/
		// 300000 => 30 sec
		// 1000000 => 10 min
		// 10000 => 10
		long timerTickerBehaviour = (long) 1000000;
		//Start now
		long tStart = System.currentTimeMillis();
		
		addBehaviour(new TickerBehaviour(this, timerTickerBehaviour) {
			private static final long serialVersionUID = 9192646164357857629L;
			protected void onTick() {
//				Long newDiff = PassingTime.checkDate(tStart);
//				// if the difference calculated equal 30 days = 1 month
//				if (newDiff == TimeUnit.DAYS.toMillis(30)) {
//					//check the satisfaction
//				}
			}
		});
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
