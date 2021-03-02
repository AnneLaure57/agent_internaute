package fr.miage.sid.agentinternaute.agent.behaviours;

import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.behaviour.SearchFiltersBehaviour;
import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Event;

public class SearchTitleBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7471857252699739681L;

	private static final Logger LOGGER = Logger.getLogger(SearchFiltersBehaviour.class.getName());

	private boolean finished = false;

	private Event event;	

	public SearchTitleBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {

		/*
		  DFAgentDescription[] distributors = ((AgentInternaute) myAgent).getAgentsDistributeurs();
		  
		  //TODO add timer Long timerStart = System.currentTimeMillis();
		  
		  LOGGER.severe("distributeurs" + distributors);
		  
		  for (DFAgentDescription distributor : distributors) {
		  
			  // Send message to distributor agent
			  ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			  request.addReceiver(distributor.getName());
			  request.setContent(title);
			  request.setContent("message from searchService"); myAgent.send(request);
		  
			  Long timerEnd = System.currentTimeMillis();
		  
			  // Wait for answer 1 minute if ((timerStart - timerEnd) < 60000) { ACLMessage
			  msg = myAgent.receive();
			  if (msg != null) { 
				  System.out.println(" - " +myAgent.getLocalName() + " <- " + msg.getContent());
		  
				  ACLMessage reply = msg.createReply();
				  reply.setPerformative(ACLMessage.INFORM); reply.setContent(" Pong");
				  myAgent.send(reply); finished = true; } 
			  block(); }
	  		}
	  	*/
		System.out.println("***************************");
		System.out.println("***************************");
		System.out.println("SearchTitleBehaviour");
		DFAgentDescription[] distributors = AgentAndACLMessageUtils.searchAgents(myAgent, AgentTypes.AGENT_DISTRIBUTEUR.getValue());
		for (DFAgentDescription distributor : distributors) {
			AgentAndACLMessageUtils.sendMessage(myAgent, ACLMessage.REQUEST, (String) event.getParameter(0), distributor.getName());
		}
	}

	@Override
	public boolean done() {
		return finished;
	}

}
