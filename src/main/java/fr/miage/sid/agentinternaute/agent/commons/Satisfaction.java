package fr.miage.sid.agentinternaute.agent.commons;

import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.entity.Profile;

public class Satisfaction {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(Satisfaction.class.getName());
	
	@SuppressWarnings("unused")
	public String satisfactionCalcul(Profile profile) {
		
		String satisfation = null;
		
		//ratio time 
		Double ratioTime = (double) (profile.getCurrentConsumptionTime() / profile.getAverageConsumptionTime());
		
		// ratio money
		Double ratioMoney = (double) (profile.getCurrentExpenses() / profile.getMaxBudget());
		
		//TODO ratio offers later

		int ratioPurchases = 0;
		
		if (ratioTime > 1 & ratioMoney > 1) {
			return satisfation = " La satisfaction pour le ratio de temps est de " + ratioTime + ", ratio dépenses : " + ratioMoney;
		} else {
			return satisfation = " La satisfaction est insuffisante. Ratio de temps est de " + ratioTime + ", ratio dépenses : " + ratioMoney;
		}
	}
}
