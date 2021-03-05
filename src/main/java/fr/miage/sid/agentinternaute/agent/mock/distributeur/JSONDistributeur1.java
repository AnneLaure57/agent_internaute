package fr.miage.sid.agentinternaute.agent.mock.distributeur;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public class JSONDistributeur1 {
	
	public static JSONObject searchTitleJSONresponse () {
		
		// Création d'une Mock réponse
//		Map<String, String> responsehMap = new HashMap<String, String>();
//		ArrayList<String> oeuvres = new ArrayList<String>();
//		oeuvres.add("Titi");
//		oeuvres.add("Tata");
//		oeuvres.add("Toto");
//		responsehMap.put("types", Arrays.toString(oeuvres.toArray()));
		
		// String response to JSON
//		JSONObject response = new JSONObject(responsehMap);

		///////////////////////////////////////////////////////////////////
		
		// Création d'une Mock réponse
		
//		JSONObject distributor = new JSONObject();
//		distributor.put("distributor", "d1");
//		response.put(distributor);
		
		/* =========================================  Abos  ============================================== */ /*=========================================*/
		
		JSONArray subscriptions = new JSONArray();
		
		JSONObject subscription1 = new JSONObject();
		subscriptions.put(subscription1);
		
		JSONObject subscription2 = new JSONObject();
		subscriptions.put(subscription2);
		
		/* ========================================= Abo 1  ============================================== */ /*=========================================*/

		subscription1.put("id", "35871");
		subscription1.put("duree", 70);
		subscription1.put("prix", 60.00);
		
		/* ========================================= Abo 2  ============================================== */ /*=========================================*/
		
		subscription2.put("id", "35415");
		subscription2.put("duree", 35);
		subscription2.put("prix", 20.35);
			
		/* ========================================= Movies ============================================== */ /*=========================================*/
					
		JSONArray movies = new JSONArray();
		
		JSONObject movie1 = new JSONObject();
		movies.put(movie1);
		
		JSONObject movie2 = new JSONObject();
		movies.put(movie2);
		
		/* ========================================= Movie 1 ============================================= */ /*=========================================*/
		
		movie1.put("id", "1006");
		movie1.put("titre", "Harry Potter et la chambre des secrets");
		movie1.put("description", "Alors que l'oncle Vernon, la tante Pétunia et son cousin Dudley reçoivent d'importants invités à diner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l'école de Poudlard et qu'il ne doit pas y retourner en septembre. Harry refuse de le croire. Mais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle-ci lui dit que la redoutable et légendaire Chambre des secrets est à nouveau ouverte, permettant ainsi à l'héritier de Serpentard de semer le chaos ? Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succèdent dans les couloirs de l'école, sans que les professeurs - pas même le populaire Gilderoy Lockhart - ne parviennent à endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.");
		movie1.put("prix", 5);
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
		
		movie2.put("id", "1033");
		movie2.put("titre", "Rox et Rouky");
		movie2.put("description", "Rox le renard et Rouky le chien sont les meilleurs amis du monde. Mais cette amitié est menacée lorsque le ma?tre de Rouky devient chasseur...");
		movie2.put("dateSortie", 1981);
		movie1.put("note", 4.5);
		
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
		
		/* ========================================= Reponse ============================================== */ /*=========================================*/
		
		JSONObject response = new JSONObject();
		response.put("abonnements", subscriptions);
		response.put("oeuvres", movies);
			
		return response;
	}

}
