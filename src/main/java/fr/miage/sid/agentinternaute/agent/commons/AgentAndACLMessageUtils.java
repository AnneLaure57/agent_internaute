package fr.miage.sid.agentinternaute.agent.commons;

import java.util.logging.Logger;

import jade.core.AID;
import jade.core.Agent;
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
public class AgentAndACLMessageUtils {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final Logger LOGGER = Logger.getLogger(AgentAndACLMessageUtils.class.getName());
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	/**
	 * Method searchAgents : to search an other agent by name.
	 * 
	 * @param serviceName The name of the other agent to search.
	 * @return Return an array of DFAgentDescription. 
	 */
	public static DFAgentDescription[] searchAgents(Agent agent, String serviceName) {
		// On créé le portrait robot de l'objet Jade que l'on cherche 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceName);
		dfd.addServices(sd);
		
		// On essaie de récupérer les agents qui match auprès du Directory Facilitator
		try {
			return DFService.search(agent, dfd);
		} catch (FIPAException e) {
			LOGGER.severe("Can't search the agent : " + serviceName);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method sendMessage : to send a JSON message (into a Java String) to a specific agent (find by it's ID).
	 * 
	 * @param message JSON message (into a Java String) to send.
	 * @param ID The ID of the agent who will receive the message.
	 */
	public static void sendMessage(Agent agent, int ACLMessageType, String message, AID ID) {
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessageType);
			aclMessage.addReceiver(ID);

			aclMessage.setContent(message);

			agent.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method replyMessage : to reply a JSON message (into a Java String) to a specific agent (find by it's ID).
	 * 
	 * @param message JSON message (into a Java String) to reply.
	 * @param ID The ID of the agent who will receive the message.
	 */
	public static void replyMessage(Agent agent, int ACLMessageType, String message, ACLMessage initialMessage) {
		try {
			ACLMessage aclMessage = initialMessage.createReply();
			
			aclMessage.setPerformative(ACLMessageType);
			aclMessage.setContent(message);

			agent.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
 