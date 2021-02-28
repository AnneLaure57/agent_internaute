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
	/* ========================================= Global ================================================ */ /*=========================================*/

	private static final Logger LOGGER = Logger.getLogger(PassingTime.class.getName());
	
	/* ========================================= Methodes ============================================== */ /*=========================================*/

	/*
	 * Vérifier la date 
	 */
// 		1 min -> 1 j
////	30 min -> 1 mois
////	1h30 -> 3 mois
////	3h -> 6 mois
////	6h -> 12 mois
////	24h -> 4 ans
	/**
	 * Method checkDate : to  
	 * 
	 * @param tStart
	 * @return 
	 */
	// TODO : fix warnings
	public static Long checkDate(long tStart) {
		
		//Set date format
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");
		//Convert Date + Timer
		Date currentDate = new Date();
		Date date2 =new Date(tStart);
		
		String currentDateTimeString = dateFormat.format(currentDate);
		String currentDateTimer = dateFormat.format(date2);
		
		LOGGER.info(" Date courante : " + currentDateTimeString +", timer : " + currentDateTimer);	
		
		//check diff between dates
		int diff = currentDate.compareTo(date2);
		Long newDiff = null;
		
//		si la diff est de 24h -> 4 ans sont écoulés
		if (diff == TimeUnit.HOURS.toMillis(24)) {
			newDiff = TimeUnit.DAYS.toMillis(4 * 365);
			
//		si la diff est de 6h -> 12 mois sont écoulés
		} else if (diff == TimeUnit.HOURS.toMillis(6)) {
			newDiff = TimeUnit.DAYS.toMillis(12 * 30);
			
//		si la diff est de 3h -> 6 mois sont écoulés
		} else if (diff == TimeUnit.HOURS.toMillis(3)) {
			newDiff = TimeUnit.DAYS.toMillis(6 * 30);
			
//		si la diff est de 1h30 -> 3 mois sont écoulés
		} else if (diff == TimeUnit.MINUTES.toMillis(90)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//		si la diff est de 30 min -> 1 mois sont écoulés
		} else if (diff == TimeUnit.MINUTES.toMillis(30)) {
			newDiff = TimeUnit.DAYS.toMillis(3 * 30);
		
//	 	si la diff est de 1 min -> 1 j sont écoulés
		} else if (diff == TimeUnit.MINUTES.toMillis(1)) {
			newDiff = TimeUnit.DAYS.toMillis(1);
		} else {
			LOGGER.info(currentDate + " est égale à " + date2);
		}
	    return newDiff;
	} 
}
