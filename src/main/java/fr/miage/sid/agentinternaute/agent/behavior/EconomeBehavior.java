package fr.miage.sid.agentinternaute.agent.behavior;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;

public class EconomeBehavior extends CyclicBehaviour{
	
	private static final Logger LOGGER = Logger.getLogger(EconomeBehavior.class.getName());
	
	/*
	 * Client EconomeBehavior
	 */
	
	private AgentInternaute economeAgent;
	
	/*
	 * Constructor
	 */
	
	public EconomeBehavior(Agent agent) {
		this.economeAgent = (AgentInternaute) economeAgent;
	}

	@Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            checkTypeMessage(message);
            block();
        }
    }

	private void checkTypeMessage(ACLMessage message) {
		 try {
	            Logger.getLogger(AgentInternaute.class.getName()).log(Level.INFO, message.getContent());
	            String messageReceived = message.getContent();
		 } catch (Exception ex) {
	            Logger.getLogger(AgentInternaute.class.getName()).log(Level.SEVERE, "Parse impossible, format JSON invalide");
	     }
	       
	}

}