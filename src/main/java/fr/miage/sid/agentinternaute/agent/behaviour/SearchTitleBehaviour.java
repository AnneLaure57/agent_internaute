package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Event;

public class SearchTitleBehaviour extends SimpleBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/
	
	private static final long serialVersionUID = -7471857252699739681L;
	
	private static final Logger LOGGER = Logger.getLogger(SearchTitleBehaviour.class.getName());
	
	/* ========================================= Attributs ============================================= */ /*=========================================*/
	
	private boolean finished = false;
	private Event event;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public SearchTitleBehaviour(Event event) {
		super();
		this.event = event;
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	@Override
	public void action() {
		LOGGER.info("Send ACL Messages to all distributors agents.");
		DFAgentDescription[] distributors = AgentAndACLMessageUtils.searchAgents(myAgent, AgentTypes.AGENT_DISTRIBUTEUR.getValue());
		
		myAgent.addBehaviour(new ParallelSearchTitleBehaviour(event, distributors));
		
		for (DFAgentDescription distributor : distributors) {
			AgentAndACLMessageUtils.sendMessage(myAgent, ACLMessage.REQUEST, (String) event.getParameter(0), distributor.getName());
		}
		finished = true;
	}

	@Override
	public boolean done() {
		return finished;
	}
}
