package fr.miage.sid.agentinternaute.agent.behaviour;


import java.util.UUID;

import org.json.JSONObject;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Event;

public class AcceptProposalBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	
	private Event event;
	private AID distributor;

	public AcceptProposalBehaviour(Event event) {
		super();
		this.event = event;
	}

	@Override
	public void action() {

		JSONObject response = new JSONObject();
		
		// We must get distributor from message
		
		try {
			if(distributor != null) {
				ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
				aclMessage.addReceiver(distributor);		
				aclMessage.setContent((String) event.getParameter(0)); 
				aclMessage.setConversationId(UUID.randomUUID().toString());

				// On créé un template pour filter les messages de retour
				MessageTemplate responseTemplate = MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchConversationId(aclMessage.getConversationId()));
				myAgent.send(aclMessage);
				
				ACLMessage resp;
				long startTime = System.currentTimeMillis();
				while (System.currentTimeMillis()-startTime < 5000) {
					resp = myAgent.receive(responseTemplate);
					if (resp != null) {
						response.put("status", resp.getContent());
						break;
					}
				}
			} else {
				response.put("status", "No distributor agent found :(");
			}
		} catch (Exception ex) {
			response.put("status", "An exception occured " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			event.notifyProcessed(response);
		}
	}

}
