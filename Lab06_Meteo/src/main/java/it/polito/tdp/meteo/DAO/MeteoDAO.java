package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, Citta citta) {

		List<Rilevamento> rilevamentiLocalitaMese = new ArrayList<Rilevamento>();
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE MONTH(data) = ? AND localita = ? ORDER BY data ASC";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);
			st.setString(2, citta.getNome());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamentiLocalitaMese.add(r);
			}

			conn.close();
			return rilevamentiLocalitaMese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public List<Citta> getAllCitta() {
		final String sql = "SELECT DISTINCT Localita FROM situazione ORDER BY localita";

		List<Citta> leCitta = new ArrayList<Citta>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
		
				Citta c = new Citta(rs.getString("Localita"));
				leCitta.add(c);
			}

			conn.close();
			return leCitta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public Double getUmiditaMedia (int mese, Citta citta) {
		Double umiditaMedia = 0.0;
		
		final String sql = "SELECT AVG(Umidita) AS U FROM situazione WHERE MONTH(data) = ? AND localita = ? ";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);
			st.setString(2, citta.getNome());
			
			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				umiditaMedia = rs.getDouble("U");
			}

			conn.close();
			return umiditaMedia;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
