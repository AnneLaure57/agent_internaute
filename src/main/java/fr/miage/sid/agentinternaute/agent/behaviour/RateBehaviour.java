package fr.miage.sid.agentinternaute.agent.behaviour;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.util.Event;

public class RateBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 5457637194175352400L;
	private Event event;

	public RateBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {

		// Send message to e-reputation agent
		DFAgentDescription ereput = AgentAndACLMessageUtils.searchAgents(myAgent, AgentTypes.AGENT_E_REPUTATION.getValue())[0];
		
		// L'objet envoyé dans l'event est le premier paramètre, c'est notre json "stringifié"
		System.out.println((String) event.getParameter(0));
		
		// json pour la réponse
		JSONObject response = new JSONObject();
		
		try {
			if(ereput != null) {
				ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
				aclMessage.addReceiver(ereput.getName());		
				aclMessage.setContent((String) event.getParameter(0)); 
				myAgent.send(aclMessage);
				
				// On retourne le status ok car l'agent e-réputation ne nous renvoie rien
				response.put("status", "ok");
			} else {
				response.put("status", "No erepute agent found :(");
			}
		} catch (Exception ex) {
			response.put("status", "An exception occured " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			event.notifyProcessed(response);
		}
	}
}
