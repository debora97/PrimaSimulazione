package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento  implements Comparable<Evento>{
	public enum TIPO{
		INIZIO_CRIMINE,
		//ARRIVA_AGENTE
		FINE_CRIMINE,
	}
	private TIPO tipo;
	private Event evento;
	private LocalDateTime data; //ordino nella coda
	private Agente agente;
	
	//Per il tipo di evento INIZIO_CRIMINE
	public Evento(TIPO tipo, Event evento, LocalDateTime data) {
		super();
		this.tipo = tipo;
		this.evento = evento;
		this.data = data;
		
	}
	
	//Per il tipo di eventi FINE_CRIMINE
	public Evento(TIPO tipo, Agente agente, LocalDateTime data) {
		this.data=data;
		this.tipo=tipo;
		this.agente=agente;
	}
	public TIPO getTipo() {
		return tipo;
	}
	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}
	public Event getEvento() {
		return evento;
	}
	public void setEvento(Event evento) {
		this.evento = evento;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Agente getAgente() {
		return agente;
	}
	public void setAgente(Agente agente) {
		this.agente = agente;
	}
	
	@Override
	public int compareTo(Evento o) {
		
		return this.data.compareTo(o.getData());
	}

	
	

}
