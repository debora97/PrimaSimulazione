package it.polito.tdp.model;

import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	private int id;
	
	private double lon;
	private double lat;
	private  Map<Distretto, Double> pippo;
	public Distretto(int id, double lon, double lat) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "Distretto [id=" + id + ", lon=" + lon + ", lat=" + lat + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	public Map<Distretto, Double> getPippo() {
		return pippo;
	}
	public void setPippo(Map<Distretto, Double> pippo) {
		this.pippo = pippo;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distretto other = (Distretto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
