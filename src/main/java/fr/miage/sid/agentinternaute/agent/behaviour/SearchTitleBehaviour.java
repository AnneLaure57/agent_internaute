package fr.miage.sid.agentinternaute.agent.behaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.agent.commons.AgentAndACLMessageUtils;
import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Event;

public class SearchTitleBehaviour extends SequentialBehaviour {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(SearchTitleBehaviour.class.getName());
	
	private JSONArray allsubscriptions = new JSONArray();
	private JSONArray alloeuvres = new JSONArray();
	private List<JSONObject> results =  new ArrayList<>();

	public SearchTitleBehaviour(Agent agent, Event event) {
		super();

		// Récupération de la liste des distributeurs auprès du DF
		DFAgentDescription[] distributors = AgentAndACLMessageUtils.searchAgents(agent,
				AgentTypes.AGENT_DISTRIBUTEUR.getValue());

		// Si on en a au moins 1, on envoie la requête
		if (distributors.length > 0) {
			LOGGER.info("Send an ACL Message to " + distributors.length + " distributors agents.");

			// On envoie en parallèle une requête à chaque distributeur
			ParallelBehaviour par = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
			this.addSubBehaviour(par);

			for (DFAgentDescription distributor : distributors) {

				par.addSubBehaviour(new OneShotBehaviour() {

					private static final long serialVersionUID = -4359660041047869386L;

					@Override
					public void action() {
						ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
						aclMessage.addReceiver(distributor.getName());
						aclMessage.setContent((String) event.getParameter(0));
						aclMessage.setConversationId(UUID.randomUUID().toString());

						// On créé un template pour filter les messages de retour
						MessageTemplate responseTemplate = MessageTemplate.and(
								MessageTemplate.MatchPerformative(ACLMessage.INFORM),
								MessageTemplate.MatchConversationId(aclMessage.getConversationId()));

						myAgent.send(aclMessage);

						ACLMessage response;
						long startTime = System.currentTimeMillis();
						while (System.currentTimeMillis()-startTime < 5000) {
							response = myAgent.receive(responseTemplate);
							if (response != null) {
								JSONObject result = new JSONObject(response.getContent());
								result.put("distributeur", response.getSender().getName());
								results.add(result);
								break;
							}
						}
					}
				});
			}

			// On traite les résultats obtenus auprès des différents distributeurs
			this.addSubBehaviour(new OneShotBehaviour() {
				
				private static final long serialVersionUID = 458952544055578744L;

				public void action() {					
					
					// Traitement des résultats avant envoi au service
					for(JSONObject result : results) {
						String distributeur = result.getString("distributeur");
						
						// Récupération des données du distributeur
						JSONArray subscriptions = result.getJSONArray("abonnements");
						JSONArray oeuvres = result.getJSONArray("oeuvres");
						
						for (int i = 0; i < oeuvres.length(); i++) {
							JSONObject oeuvre = oeuvres.getJSONObject(i);
							oeuvre.put("distributeur", distributeur);
							alloeuvres.put(oeuvre);							
						}	
						
						for (int i = 0; i < subscriptions.length(); i++) {
							JSONObject subscription = subscriptions.getJSONObject(i);
							subscription.put("distributeur", distributeur);
							allsubscriptions.put(subscription);							
						}	
					}
					
					// Ajout des deux arrays dans le json
					JSONObject response = new JSONObject();
					response.put("abonnements", allsubscriptions);
					response.put("oeuvres", alloeuvres);
					
					System.out.println("Agent " + myAgent.getName() + " got results from " + results.size() + " distributors.");
					
					// Envoi de la reponse
					event.notifyProcessed(response);
				}
			});
		}
	}
}