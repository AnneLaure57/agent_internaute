package fr.miage.sid.agentinternaute.agent.commons;

import fr.miage.sid.agentinternaute.entity.Profile;

public class Satisfaction {
	
	public String satisfactionCalcul(Profile profile) {
		
		@SuppressWarnings("unused")
		String satisfation = null;
		
		// ratio time
		//curentConsom / averageConsom
		Double 	ratioTime = (double) ((profile.getCurrentConsumptionTime() / profile.getAverageConsumptionTime()) * 100);
		
		// ratio money
		//curentConsom / averageConsom
		Double ratioMoney = (double) (( profile.getCurrentExpenses() / profile.getMaxBudget()) * 100);
		
		if (ratioTime >= 95 && ratioMoney >= 95) {
			return satisfation = " Service impeccable. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		} else if (ratioTime >= 90 && ratioMoney >= 90) {
			return satisfation = " Très satisfait(e) du service proposé. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		//70 to 90 and not 80 to 90 or 70 to 80
		} else if (ratioTime >= 70 && ratioMoney >= 70) {
			return satisfation = " Satisfait(e) du service proposé. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		} else if (ratioTime >= 60 && ratioMoney >= 60) {
			return satisfation = " Moyennement satisfait(e) du service proposé. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		} else if (ratioTime >= 50 && ratioMoney >= 50) {
			return satisfation = " Peu satisfait(e) du service proposé. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		} else {
			return satisfation = " Pas satisfait(e) du service proposé. Ratio temps : " + ratioTime + "%, ratio dépenses : " + ratioMoney + "%";
		}
	}
}
