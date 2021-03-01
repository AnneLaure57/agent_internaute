package fr.miage.sid.agentinternaute.agent.behaviours;

import java.util.logging.Logger;

import jade.core.behaviours.SimpleBehaviour;
import jade.util.Event;

public class SearchFiltersBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -6264068510790501411L;
	private boolean finished = false;
	@SuppressWarnings("unused")
	private Event event;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(SearchFiltersBehaviour.class.getName());

	public SearchFiltersBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {

		/*DFAgentDescription[] distributors = ((AgentInternaute) myAgent).getAgentsDistributeurs();
		
		//TODO add timer
		Long timerStart = System.currentTimeMillis();
		
		LOGGER.severe("distributeurs" + distributors);
		for (DFAgentDescription distributor : distributors) {
			
			// Send message to distributor agent
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.addReceiver(distributor.getName());	
			//assure the arraylist of filters is convert to string
			//request.setContent(message);
			request.setContent("message from searchService");
			myAgent.send(request);
			
			Long timerEnd = System.currentTimeMillis();

			// Wait for answer 1 minute
			if ((timerStart - timerEnd) < 60000) {
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println(" - " + myAgent.getLocalName() + " <- " + msg.getContent());

					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(" Pong");
					myAgent.send(reply);
					finished = true;
				}
			}
			block();
		}*/
	}

	@Override
	public boolean done() {
		return finished;
	}
}
