package fr.miage.sid.agentinternaute.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.miage.sid.agentinternaute.entity.Profile;

public class Econome {

	private static final Logger LOGGER = Logger.getLogger(Econome.class.getName());
	private static final String KEY_NAME = "prix";

	public JSONObject economeResponse(JSONObject response, Profile profil) {

		////////////////////// Oeuvres
		
		JSONArray oeuvres = response.getJSONArray("oeuvres");
		JSONArray sortedOeuvres = new JSONArray();

		// On met les oeuvres du JSONArray dans une liste
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < oeuvres.length(); i++) {
			jsonValues.add(oeuvres.getJSONObject(i));
		}

		// On trie
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject a, JSONObject b) {
				Double valA = null, valB = null;

				try {
					valA = a.getDouble(KEY_NAME);
					valB = b.getDouble(KEY_NAME);					
				} catch (JSONException e) {
					LOGGER.severe("Cannot sort JSONArray");
				}
				
				if(valA == null) valA = 1000000.0;
				if(valB == null) valB = 1000000.0;

				return valA.compareTo(valB);
			}
		});

		// On remet les objets dans le JSONArray
		for (int i = 0; i < oeuvres.length(); i++) {
			sortedOeuvres.put(jsonValues.get(i));
		}
		
		////////////////////// Abonnements

		JSONArray offres = response.getJSONArray("abonnements");
		JSONArray sortedSubscriptions = new JSONArray();

		// On met les oeuvres du JSONArray dans une liste
		List<JSONObject> jsonValues2 = new ArrayList<JSONObject>();
		for (int i = 0; i < offres.length(); i++) {
			jsonValues2.add(offres.getJSONObject(i));
		}

		// On trie
		Collections.sort(jsonValues2, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject a, JSONObject b) {
				Double valA = null, valB = null;

				try {
					valA = a.getDouble(KEY_NAME);
					valB = b.getDouble(KEY_NAME);
				} catch (JSONException e) {
					LOGGER.warning("Cannot sort JSONArray");
				}

				return valA.compareTo(valB);
			}
		});

		// On remet les objets dans le JSONArray
		for (int i = 0; i < offres.length(); i++) {
			sortedSubscriptions.put(jsonValues2.get(i));
		}

		// On remet tout ensemble
		JSONObject sorted = new JSONObject();
		sorted.put("oeuvres", sortedOeuvres);
		sorted.put("abonnements", sortedSubscriptions);

		return sorted;
	}
}
