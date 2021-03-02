package fr.miage.sid.agentinternaute.agent.mock.distributeur;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public class JSONDistributeur2 {
	
	public static JSONObject searchTitleJSONresponse () {
		
		/* =========================================  Abos  ============================================== */ /*=========================================*/
		
		JSONArray subscriptions = new JSONArray();
		
		JSONObject subscription1 = new JSONObject();
		subscriptions.put(subscription1);
		
		JSONObject subscription2 = new JSONObject();
		subscriptions.put(subscription2);
		
		
		/* ========================================= Abo 1  ============================================== */ /*=========================================*/

		subscription1.put("id", "358541");
		subscription1.put("duree", 60);
		subscription1.put("prix", 40.05);
		
		/* ========================================= Abo 2  ============================================== */ /*=========================================*/
		
		subscription2.put("id", "35755");
		subscription2.put("duree", 120);
		subscription2.put("prix", 60.35);
		
		/* ========================================= Movies ============================================== */ /*=========================================*/
					
		JSONArray movies = new JSONArray();
		
		JSONObject movie1 = new JSONObject();
		movies.put(movie1);
		
		JSONObject movie2 = new JSONObject();
		movies.put(movie2);
		
		JSONObject movie3 = new JSONObject();
		movies.put(movie3);
		
		/* ========================================= Movie 1 ============================================= */ /*=========================================*/
		
		movie1.put("id", "1006");
		movie1.put("titre", "Harry Potter et la chambre des secrets");
		movie1.put("description", "Alors que l\u0027oncle Vernon, la tante Pétunia et son cousin Dudley re?oivent d\u0027importants invités ? d?ner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l\u0027école de Poudlard et qu\u0027il ne doit pas y retourner en septembre. Harry refuse de le croire.\u003cbr /\u003eMais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle-ci lui dit que la redoutable et légendaire Chambre des secrets est ? nouveau ouverte, permettant ainsi ? l\u0027héritier de Serpentard de semer le chaos ? Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succ?dent dans les couloirs de l\u0027école, sans que les professeurs - pas m?me le populaire Gilderoy Lockhart - ne parviennent ? endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.");
		movie1.put("prix", 4.35);
		movie1.put("dateSortie", 2002);
		movie1.put("note", 3.5);
		
		JSONArray genres = new JSONArray();
		JSONObject genre1 = new JSONObject();
		genre1.put("id", "6");
		genre1.put("nom", "Fantastique");
		movie1.put("genres", genres);
		
		JSONArray acteurs = new JSONArray();
		JSONObject acteur1 = new JSONObject();
		acteur1.put("id", "57530");
		acteur1.put("nom", "Watson");
		acteur1.put("prenom", "Emma");
		acteurs.put(acteur1);
		JSONObject acteur2 = new JSONObject();
		acteur2.put("id", "29169");
		acteur2.put("nom", "Grint");
		acteur2.put("prenom", "Rupert");
		acteurs.put(acteur2);
		JSONObject acteur3 = new JSONObject();
		acteur3.put("id", "36582");
		acteur3.put("nom", "Radcliffe");
		acteur3.put("prenom", "Daniel");
		acteurs.put(acteur3);
		movie1.put("acteurs", acteurs);
		
		JSONArray realisateurs = new JSONArray();
		JSONObject realisateur1 = new JSONObject();
		realisateur1.put("id", "7670");
		realisateur1.put("nom", "Columbus");
		realisateur1.put("prenom", "Chris");
		realisateurs.put(realisateur1);
		movie1.put("realisateurs", realisateurs);
				
		/* ========================================= Movie 2 ============================================= */ /*=========================================*/
		
		movie2.put("id", "10965");
		movie2.put("titre", "JOJO");
		movie2.put("description", "Jojo et ces super histoires !!!");
		movie2.put("dateSortie", 2015);
		
		JSONArray genres2 = new JSONArray();
		JSONObject genre21 = new JSONObject();
		genre21.put("id", "7");
		genre21.put("nom", "Animation");
		movie2.put("genres", genres2);
		
		JSONArray acteurs2 = new JSONArray();
		JSONObject acteur21 = new JSONObject();
		acteur21.put("id", "2316");
		acteur21.put("nom", "Rooney");
		acteur21.put("prenom", "Mickey");
		acteurs2.put(acteur21);
		JSONObject acteur22 = new JSONObject();
		acteur22.put("id", "1475");
		acteur22.put("nom", "Russell");
		acteur22.put("prenom", "Kurt");
		acteurs2.put(acteur22);
		JSONObject acteur23 = new JSONObject();
		acteur23.put("id", "1000250");
		acteur23.put("nom", "Salez");
		acteur23.put("prenom", "Morvan");
		acteurs2.put(acteur23);
		movie2.put("acteurs", acteurs2);
		
		JSONArray realisateurs2 = new JSONArray();
		JSONObject realisateur21 = new JSONObject();
		realisateur21.put("id", "114082");
		realisateur21.put("nom", "Rich");
		realisateur21.put("prenom", "Richard");
		realisateurs2.put(realisateur21);
		movie2.put("realisateurs", realisateurs2);
		
		/* ========================================= Movie 3 ============================================= */ /*=========================================*/
		
		movie3.put("id", "10978");
		movie3.put("titre", "A la main et Jaspine");
		movie3.put("description", "Toi t'es vraiment un gros coquin ;)");
		movie3.put("dateSortie", 2015);
		
		JSONArray genres3 = new JSONArray();
		JSONObject genre31 = new JSONObject();
		genre31.put("id", "7");
		genre31.put("nom", "Animation");
		movie3.put("genres", genres3);
		
		JSONArray acteurs3 = new JSONArray();
		JSONObject acteur31 = new JSONObject();
		acteur31.put("id", "2316");
		acteur31.put("nom", "Rooney");
		acteur31.put("prenom", "Mickey");
		acteurs3.put(acteur31);
		
		JSONArray realisateurs3 = new JSONArray();
		JSONObject realisateur31 = new JSONObject();
		realisateur31.put("id", "114082");
		realisateur31.put("nom", "Rich");
		realisateur31.put("prenom", "Richard");
		realisateurs3.put(realisateur31);
		movie3.put("realisateurs", realisateurs3);
		
		/* ========================================= Reponse ============================================== */ /*=========================================*/
		
		JSONObject response = new JSONObject();
		response.put("abonnements", subscriptions);
		response.put("oeuvres", movies);
			
		return response;
	}

}
