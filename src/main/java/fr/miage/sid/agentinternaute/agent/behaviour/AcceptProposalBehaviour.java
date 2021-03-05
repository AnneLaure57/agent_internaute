package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Event;

public class AcceptProposalBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AcceptProposalBehaviour.class.getName());

	private Event event;
	private Agent agent;

	public AcceptProposalBehaviour(Agent agent, Event event) {
		super();
		this.event = event;
		this.agent = agent;
	}

	@Override
	public void action() {

		// Extraction of distributor name
		JSONObject message = new JSONObject((String) event.getParameter(0));
		String chosenDistributor = message.has("distributor") ? message.getString("distributor") : "";

		// We must get distributor from message
		DFAgentDescription[] distributors = AgentAndACLMessageUtils.searchAgents(agent,
				AgentTypes.AGENT_DISTRIBUTEUR.getValue());

		JSONObject response = new JSONObject();

		try {
			for (DFAgentDescription distributor : distributors) {

				if (distributor.getName().getName().toString().equals(chosenDistributor)) {
					LOGGER.info("Send an ACL Message to " + chosenDistributor + " "
							+ distributor.getName().getName().toString());
					ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
					aclMessage.addReceiver(distributor.getName());
					aclMessage.setContent(message.toString());
					aclMessage.setConversationId(UUID.randomUUID().toString());

					// On créé un template pour filter les messages de retour
					MessageTemplate responseTemplate = MessageTemplate.and(
							MessageTemplate.MatchPerformative(ACLMessage.INFORM),
							MessageTemplate.MatchConversationId(aclMessage.getConversationId()));
					myAgent.send(aclMessage);
					LOGGER.info("Message sent to " + chosenDistributor);

					ACLMessage resp;
					long startTime = System.currentTimeMillis();
					while (System.currentTimeMillis() - startTime < 5000) {
						resp = myAgent.receive(responseTemplate);
						if (resp != null) {
							response.put("status", resp.getContent());
							break;
						}
					}
				} else {
					response.put("status", "No distributor agent found :(");
				}
			}
		} catch (Exception ex) {
			response.put("status", "An exception occured " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			event.notifyProcessed(response);
		}
	}

}
