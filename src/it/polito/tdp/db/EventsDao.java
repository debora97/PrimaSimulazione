package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.model.Distretto;
import it.polito.tdp.model.Event;

public class EventsDao {

	public List<Event> listAllEvents() {
		String sql = "SELECT * FROM events";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Event> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"), res.getInt("offense_code"),
							res.getInt("offense_code_extension"), res.getString("offense_type_id"),
							res.getString("offense_category_id"), res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"), res.getDouble("geo_lon"), res.getDouble("geo_lat"),
							res.getInt("district_id"), res.getInt("precinct_id"), res.getString("neighborhood_id"),
							res.getInt("is_crime"), res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnni() {
		String sql = "SELECT DISTINCT YEAR(reported_date) as r " + "FROM `events` ";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Integer> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Integer(res.getInt("r")));
				} catch (Throwable t) {
					t.printStackTrace();

				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Distretto> getCentro(int anno) {
		String sql = "SELECT   e.district_id , AVG(e.geo_lon) as lon , AVG(e.geo_lat) as lat " + "FROM `events` AS e "
				+ "WHERE YEAR(e.reported_date)=? " + "GROUP BY e.district_id  ";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			List<Distretto> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Distretto(res.getInt("district_id"), res.getDouble("lon"), res.getDouble("lat")));
				} catch (Throwable t) {
					t.printStackTrace();

				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Event> listEventiDaConsiderare(int anno, int mese, int giorno ) {
		String sql = "SELECT * " + 
				"				FROM EVENTS  " + 
				"				WHERE YEAR(reported_date) = ? AND MONTH(reported_date) =?  AND DAY(reported_date)=? ";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			List<Event> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"), res.getInt("offense_code"),
							res.getInt("offense_code_extension"), res.getString("offense_type_id"),
							res.getString("offense_category_id"), res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"), res.getDouble("geo_lon"), res.getDouble("geo_lat"),
							res.getInt("district_id"), res.getInt("precinct_id"), res.getString("neighborhood_id"),
							res.getInt("is_crime"), res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Distretto getDistrettoMenoCrimini(int anno, Map<Integer, Distretto> mappaDistretti) {
		String sql = "SELECT id " + "FROM  (SELECT e.district_id AS id, COUNT(*) AS num  " + "FROM `events` AS e "
				+ "WHERE YEAR(e.reported_date)=? " + "GROUP BY e.district_id  ) AS tab "
				+ "WHERE num=(SELECT MIN(num) FROM  (SELECT e.district_id AS id, COUNT(*) AS num  "
				+ "FROM `events` AS e " + "WHERE YEAR(e.reported_date)=? " + "GROUP BY e.district_id  ) AS t ) ";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			Distretto d = null;

			ResultSet res = st.executeQuery();
			if (res.next()) {
				int id = res.getInt("id");
				d = mappaDistretti.get(id);
				return d;
			}
			conn.close();
			return null;

		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}

	}

}
