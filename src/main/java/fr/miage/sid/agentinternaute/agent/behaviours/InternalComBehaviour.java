package fr.miage.sid.agentinternaute.agent.behaviours;

import fr.miage.sid.agentinternaute.agent.AgentInternaute;
import jade.core.behaviours.CyclicBehaviour;

public class InternalComBehaviour extends CyclicBehaviour {

	public InternalComBehaviour(AgentInternaute agentInternaute) {
		super(agentInternaute);
	}

	@Override
	public void action() {
//		 BookInfo info = (BookInfo) myAgent.getO2AObject();
//	        System.out.println("bookInfo: " + info);
//		if (true) {
//	            purchase(info.getTitle(), info.getMinPrice(), info
//	                .getMaxPrice(), info.getDeadline());
//	            myGui
//	                .notifyUser(title：" + info.getTitle() + ",minPrice："
//	                    + info.getMinPrice() + ",maxPrice："
//	                    + info.getMaxPrice());
//		} else {
//			block();
//		}

		Object info = myAgent.getO2AObject();
		if (info != null) {
			// do something with Event
//            event.notifyProcessed(new SomeObjectFromAgent());
		} else {
			block();
		}
	}
}
