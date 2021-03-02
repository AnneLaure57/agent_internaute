package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Event;

public class SearchTitleBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7471857252699739681L;
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(SearchFiltersBehaviour.class.getName());

	
	
	
	private boolean finished = false;
	@SuppressWarnings("unused")
	private Event event;

	
	
	
	

	public SearchTitleBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {
		DFAgentDescription[] distributors = AgentAndACLMessageUtils.searchAgents(myAgent, AgentTypes.AGENT_DISTRIBUTEUR.getValue());
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
