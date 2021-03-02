package fr.miage.sid.agentinternaute.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RequestBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -7640679757105942415L;
	private int step;
	private AID bestSeller; // The agent distributor who provides the best offer
	private int bestPrice; // The best offered price from distributor
	private int repliesCnt = 0; // The counter of replies from seller agents

	@Override
	public void action() {
		String message = "Lords of Rings";
		//TODO check if cases working
		switch (step) 
		{
		 case 1:
			 // Receive all proposals/refusals from seller agents
			 //myAgent.addBehaviour(new OfferRequests());
			 ACLMessage reply = myAgent.receive();
			 System.out.println("réponse de l'agent distributeur : " + reply);
			 if (reply != null) {
				 // Reply received
				 if (reply.getPerformative() == ACLMessage.REQUEST) {
					 // This is an offer
					 int price = Integer.parseInt(reply.getContent());
					 if (bestSeller == null || price < bestPrice) {
						 // This is the best offer at present
						 bestPrice = price;
						 bestSeller = reply.getSender();
					 } else {
						 //Refuse the proposition
						ACLMessage response = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
						//Return to the sender
						response.addReceiver(reply.getSender());	
						response.setContent("proposition refusée");
						//send the message
						myAgent.send(response);
					 }
				 }
				 repliesCnt++;
				 if (repliesCnt >= 2) {
					 // We received all replies
					 step = 2;
				 }
			 } else {
				 block();
			 }
			 break;
		 case 2:
			 // Send the purchase order to the seller that provided the best offer
			 ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			 order.addReceiver(bestSeller);
			 order.setReplyWith("achat"+System.currentTimeMillis());
			 myAgent.send(order);
			 MessageTemplate.MatchInReplyTo(order.getReplyWith());
			 step = 3;
			 break;
		 case 3:
			 // Receive the purchase order reply
			 reply = myAgent.receive();
			 if (reply != null) {
				 // Purchase order reply received
				 if (reply.getPerformative() == ACLMessage.INFORM) {
					 // Purchase successful. We can terminate
					 System.out.println(message + "achat effectué avec succès");
					 System.out.println("Prix = "+bestPrice);
					 myAgent.doDelete();
				 }
				 step = 4;
			 }
			 else {
				 block();
			 }
			break;
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
