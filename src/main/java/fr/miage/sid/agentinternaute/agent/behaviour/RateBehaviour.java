package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.UUID;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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
		
		JSONObject response = new JSONObject();

		// Send message to e-reputation agent
		DFAgentDescription ereput = AgentAndACLMessageUtils.searchAgents(myAgent, AgentTypes.AGENT_E_REPUTATION.getValue())[0];
		
		try {
			if(ereput != null) {
				ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
				aclMessage.addReceiver(ereput.getName());		
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
