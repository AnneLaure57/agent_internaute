package fr.miage.sid.agentinternaute.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonDBService {

	private final Logger LOGGER = Logger.getLogger(CommonDBService.class.getName());
	private Connection makeJDBCConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE,
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly.");
			e.printStackTrace();
			return null;
		}

		try {
			Connection db = DriverManager.getConnection(
					"jdbc:mysql://mysql-agentmiage2021.alwaysdata.net/agentmiage2021_dbpropre", "227750", "mdpUser");
			if (db == null) {
				LOGGER.log(Level.SEVERE, "Failed to make connection!");
			}
			return db;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL Connection Failed!");
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Récupération des acteurs
	 */
	public JSONArray getActors() {
		JSONArray actors = new JSONArray();
		try {
			Connection db = makeJDBCConnection();
			
			String statement = "SELECT * FROM personne INNER JOIN acteurs ON acteurs.id_personne = personne.id ";

			PreparedStatement stmta = db.prepareStatement(statement);
			stmta.execute();
			ResultSet rsa = stmta.getResultSet();
			
			JSONObject acteur;
			while (rsa.next()) {
				acteur = new JSONObject();
				acteur.put("id", rsa.getInt("id"));
				acteur.put("name", rsa.getString("nom") + " " + rsa.getString("prenom"));
				actors.put(acteur);
			}

			stmta.close();
			db.close();

			return actors;
			
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed for actors!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Récupération des réalisateurs
	 */
	public JSONArray getDirectors() {
		JSONArray directors = new JSONArray();
		try {
			Connection db = makeJDBCConnection();
			
			String statement = "SELECT * FROM personne INNER JOIN realisateurs ON realisateurs.id_personne = personne.id";

			PreparedStatement stmtd = db.prepareStatement(statement);
			stmtd.execute();
			ResultSet rsd = stmtd.getResultSet();
			JSONObject realisateur;
			
			while (rsd.next()) {
				realisateur = new JSONObject();
				realisateur.put("id", rsd.getInt("id"));
				realisateur.put("name", rsd.getString("nom") + " " + rsd.getString("prenom"));
				directors.put(realisateur);
			}

			stmtd.close();
			db.close();
			
			return directors;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed for directors!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Récupération des artistes de musiques
	 */
	public JSONArray getArtists() {
		JSONArray artists = new JSONArray();
		try {
			Connection db = makeJDBCConnection();
			
			String statement = "SELECT * FROM artists ORDER BY name";

			PreparedStatement stmtc = db.prepareStatement(statement);
			stmtc.execute();
			ResultSet rsc = stmtc.getResultSet();
			JSONObject artist;
			
			while (rsc.next()) {
				artist = new JSONObject();
				artist.put("id", rsc.getInt("id"));
				artist.put("name", rsc.getString("name"));
				artists.put(artist);
			}

			stmtc.close();
			db.close();
			
			return artists;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed for artists!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Récupération des genres des films (/série)
	 */
	public JSONArray getVideoGenres() {		
		try {
			JSONArray video_genres = new JSONArray();
			
			Connection db = makeJDBCConnection();
			
			String statement = "SELECT * FROM genre ORDER BY nom";

			PreparedStatement stmtv = db.prepareStatement(statement);
			stmtv.execute();
			ResultSet rsv = stmtv.getResultSet();
			JSONObject genre;
			
			while (rsv.next()) {
				genre = new JSONObject();
				genre.put("id", rsv.getInt("id"));
				genre.put("name", rsv.getString("nom"));
				video_genres.put(genre);
			}

			stmtv.close();
			db.close();

			return video_genres;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed for video genres!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Récupération de genre de musiques  
	 */
	public JSONArray getMusicGenres() {
		try {
			JSONArray music_genres = new JSONArray();
			
			Connection db = makeJDBCConnection();
			
			String statement = "SELECT * FROM musical_genres ORDER BY name";

			PreparedStatement stmtm = db.prepareStatement(statement);
			stmtm.execute();
			ResultSet rsm = stmtm.getResultSet();
			JSONObject genre;
			
			while (rsm.next()) {
				genre = new JSONObject();
				genre.put("id", rsm.getInt("id"));
				genre.put("name", rsm.getString("name"));
				music_genres.put(genre);
			}

			stmtm.close();
			db.close();
			
			return music_genres;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed for music genres!");
			e.printStackTrace();
		}
		return null;
	}

	/* ========================================= Accesseurs ============================================ */ /*=========================================*/

	/* ========================================= Main ================================================== */ /*=========================================*/
}