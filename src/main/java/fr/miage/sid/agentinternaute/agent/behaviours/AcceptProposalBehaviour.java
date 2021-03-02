package fr.miage.sid.agentinternaute.agent.behaviours;


import jade.core.behaviours.SimpleBehaviour;
import jade.util.Event;

public class AcceptProposalBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private Event event;

	public AcceptProposalBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
