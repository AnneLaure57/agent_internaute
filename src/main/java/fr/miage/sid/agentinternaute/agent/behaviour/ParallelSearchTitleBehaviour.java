package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.logging.Logger;

import org.json.JSONArray;

import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.util.Event;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 * Ex de doc : https://djug.developpez.com/java/jade/behaviours/
 */
public class ParallelSearchTitleBehaviour extends SequentialBehaviour {
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final long serialVersionUID = 4023562697885420376L;
	
	private static final Logger LOGGER = Logger.getLogger(ParallelSearchTitleBehaviour.class.getName());

	/* ========================================= Attributs ============================================= */ /*=========================================*/

//	private Event event;
//	
//	private DFAgentDescription[] distributors;
//	
//	private JSONArray response;
	
	/* ========================================= Constructeurs ========================================= */ /*=========================================*/

	public ParallelSearchTitleBehaviour(Event event, DFAgentDescription[] distributors) {
//		super(ParallelBehaviour.WHEN_ALL);
//		this.event = event;
//		this.distributors = distributors;
//		this.response = new JSONArray();
		
		JSONArray response = new JSONArray();
		
		LOGGER.info("===========================================================");
		LOGGER.info("Call the ParallelSearchTitleBehaviour.");
		
		for (DFAgentDescription distributor : distributors) {
			this.addSubBehaviour(new HandleSearchTitleBehaviour(response, distributor));
		}
		
		System.out.println("Tu es là : ParallelSearchTitleBehaviour !");
		System.out.println(response.toString());
		
		// Transmit to API
		event.notifyProcessed(response.toString());
	}
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/

}
