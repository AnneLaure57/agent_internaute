package fr.miage.sid.agentinternaute.agent.behaviours;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.behaviours.SimpleBehaviour;

public class SearchTitleBehaviour extends SimpleBehaviour {

	private boolean finished = false;

	public SearchTitleBehaviour(AgentInternaute agentInternaute) {
		super(agentInternaute);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		return finished;
	}

}
