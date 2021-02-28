package fr.miage.sid.agentinternaute.agent.behaviours;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class RateBehaviour extends SimpleBehaviour {

	private boolean finished = false;

	public RateBehaviour(AgentInternaute agentInternaute) {
		super(agentInternaute);
	}

	@Override
	public void action() {

		// Send message to e-reputation agent
		DFAgentDescription ereput = ((AgentInternaute) myAgent).getAgentReputation();
		try {
			ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
			aclMessage.addReceiver(ereput.getName());		
			aclMessage.setContent("yo");
			myAgent.send(aclMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finished = true;
	}

	@Override
	public boolean done() {
		return finished;
	}

}
