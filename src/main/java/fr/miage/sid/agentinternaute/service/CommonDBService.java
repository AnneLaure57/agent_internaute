package fr.miage.sid.agentinternaute.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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

	static Connection ConnectionBDD = null;
	static PreparedStatement PreparedStatementBdd = null;

	private void makeJDBCConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE,
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly.");
			e.printStackTrace();
			return;
		}

		try {
			ConnectionBDD = DriverManager.getConnection(
					"jdbc:mysql://mysql-agentmiage2021.alwaysdata.net/agentmiage2021_dbpropre", "227750", "mdpUser");
			if (ConnectionBDD == null) {
				LOGGER.log(Level.SEVERE, "Failed to make connection!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
	}
	
	/*
	 * 
	 */
	public List<String> getActors() {

		try {
			makeJDBCConnection();
			
			String statement = "SELECT * FROM acteurs";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();

			while (rs.next()) {
				int idFilm = rs.getInt("id_film");
				String originalTitle = rs.getString("title");
				String titre = rs.getString("titre");
				String resume = rs.getString("resume");
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return null;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public List<String> getDirectors() {

		try {
			makeJDBCConnection();
			
			String statement = "SELECT * FROM director";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();

			while (rs.next()) {
				int idFilm = rs.getInt("id_film");
				String originalTitle = rs.getString("title");
				String titre = rs.getString("titre");
				String resume = rs.getString("resume");
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return null;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public JSONArray getArtists() {
		JSONArray artists = new JSONArray();
		try {
			makeJDBCConnection();
			
			String statement = "SELECT * FROM artists ORDER BY name";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();

			while (rs.next()) {
				String name = rs.getString("name");
				artists.put(new JSONObject("name", name));
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return artists;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public List<String> getVideoGenres() {

		try {
			makeJDBCConnection();
			
			String statement = "SELECT * FROM genre";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();

			while (rs.next()) {
				int idFilm = rs.getInt("id_film");
				String originalTitle = rs.getString("title");
				String titre = rs.getString("titre");
				String resume = rs.getString("resume");
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return null;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 */
	public JSONArray getMusicGenres() {
		try {
			JSONArray genres = new JSONArray();
			
			makeJDBCConnection();
			
			String statement = "SELECT * FROM musical_genres ORDER BY name";

			PreparedStatementBdd = ConnectionBDD.prepareStatement(statement);
			PreparedStatementBdd.execute();
			ResultSet rs = PreparedStatementBdd.getResultSet();

			while (rs.next()) {
				String name = rs.getString("name");
				genres.put(name);
			}

			PreparedStatementBdd.close();
			ConnectionBDD.close();
			
			return genres;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "MySQL query Failed!");
			e.printStackTrace();
		}
		return null;
	}
}