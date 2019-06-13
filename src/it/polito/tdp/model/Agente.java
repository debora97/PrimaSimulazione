package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Agente {
	private int id;
	private Distretto distretto;
	private boolean eoccupato;
	
	public Agente(int id, Distretto distretto ) {
		
		this.id = id;
		this.distretto = distretto;
		this.eoccupato = false;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Distretto getDistretto() {
		return distretto;
	}
	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}
	public boolean isEoccupato() {
		return eoccupato;
	}
	public void setEoccupato(boolean eoccupato) {
		this.eoccupato = eoccupato;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agente other = (Agente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
