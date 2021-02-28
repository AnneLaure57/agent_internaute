package fr.miage.sid.agentinternaute.commons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public class PassingTime {

	/*
	 * Vérifier la date 
	 */
// 		1 min -> 1 j
////	30 min -> 1 mois
////	1h30 -> 3 mois
////	3h -> 6 mois
////	6h -> 12 mois
////	24h -> 4 ans
	// TODO : fix warnings
	@SuppressWarnings("unused")
	private void checkDate(long tStart) {
		
		//Set date format
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");
		//Convert Date + Timer
		Date currentDate = new Date();
		Date date2 =new Date(tStart);
		
		String currentDateTimeString = dateFormat.format(currentDate);
		String currentDateTimer = dateFormat.format(date2);
		
		System.out.println(" Date courante : " + currentDateTimeString +", timer : " + currentDateTimer);	
		
		//check diff between dates
		int diff = currentDate.compareTo(date2);
		Long newDiff = null;
		
//		 24h -> 4 ans
		if (diff == TimeUnit.HOURS.toMillis(24)) {
			newDiff = TimeUnit.DAYS.toMillis(4 * 365);
//		6h -> 12 mois
		} else if (diff == TimeUnit.HOURS.toMillis(6)) {
			newDiff = TimeUnit.DAYS.toMillis(12 * 30);
//		3h -> 6 mois
		} else if (diff == TimeUnit.HOURS.toMillis(3)) {
			newDiff = TimeUnit.DAYS.toMillis(6 * 30);
//		 1h30 -> 3 mois
		} else if (diff == TimeUnit.MINUTES.toMillis(90)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//		30 min -> 1 mois
		} else if (diff == TimeUnit.MINUTES.toMillis(30)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//	 		1 min -> 1 j		
		} else if (diff == TimeUnit.MINUTES.toMillis(1)) {
			newDiff = TimeUnit.DAYS.toMillis(1);
		} else {
			System.out.println(currentDate + " is equal to " + date2);
		}
	} 
}
