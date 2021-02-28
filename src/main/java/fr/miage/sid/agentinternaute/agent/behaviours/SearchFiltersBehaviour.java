package fr.miage.sid.agentinternaute.agent.behaviours;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SearchFiltersBehaviour extends SimpleBehaviour {

	private boolean finished = false;

	public SearchFiltersBehaviour(AgentInternaute agentInternaute) {
		super(agentInternaute);
	}

	@Override
	public void action() {

		DFAgentDescription[] distributors = ((AgentInternaute) myAgent).getAgentsDistributeurs();
		for (DFAgentDescription distributor : distributors) {
			// Send message to distributor agent
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.addReceiver(distributor.getName());		
			request.setContent("yo");
			myAgent.send(request);

			// Wait for answer
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				System.out.println(" - " + myAgent.getLocalName() + " <- " + msg.getContent());

				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(" Pong");
				myAgent.send(reply);
			}

			if (msg != null) {
				System.out.println(" - " + myAgent.getLocalName() + " <- " + msg.getContent());
				finished = true;
			}
			block();
		}
	}

	@Override
	public boolean done() {
		return finished;
	}

}
