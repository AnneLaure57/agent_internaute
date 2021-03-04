package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.behaviours.CyclicBehaviour;
import jade.util.Event;

public class InternalComBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(InternalComBehaviour.class.getName());
	

	public InternalComBehaviour(AgentInternaute agentInternaute) {
		super(agentInternaute);
	}

	@Override
	public void action() {		
		// Recupération de l'event envoyé par le service
		Event event = (Event) myAgent.getO2AObject(); 
		
		// Event types
		// 0 -> Envoi des notes
		// 1 -> Recherche par titre
		// 2 -> Recherche par filtres
		// 3 -> Envoi acceptation de proposition
		
		// On dispatche à nos autres behaviours en fonction du type de l'event
		if (event != null) {
			if(event.getType() == 0) {
				myAgent.addBehaviour(new RateBehaviour(event));
			} else if(event.getType() == 1) {
				myAgent.addBehaviour(new SearchTitleBehaviour(myAgent, event));
			} else if(event.getType() == 2) {
				myAgent.addBehaviour(new SearchFiltersBehaviour(myAgent, event));
			} else if(event.getType() == 3) {
				myAgent.addBehaviour(new AcceptProposalBehaviour(null, event));
			} else {
				LOGGER.warning("Wrong event type was sent !");
			}
		} else {
			block();
		}
	}
}
