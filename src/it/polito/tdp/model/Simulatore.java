package it.polito.tdp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Simulatore {
	// modello stato del sistema
	private SimpleWeightedGraph<Distretto, DefaultWeightedEdge> grafo;
	private List<Event> eventiDaSimulare;
	private Random rand;
	// coda prioritaria
	private PriorityQueue<Evento> queue;

	// parametri di simulazione
	private int N_AGENTI;
	private Distretto partenza;
	private List<Agente> agenti;

	// valori di output
	private int n_malGestiti;

	public void init(SimpleWeightedGraph grafo, int N, Distretto partenza, List<Event> eventi) {
		this.N_AGENTI = N;

		this.grafo = grafo;
		this.n_malGestiti = 0;

		queue = new PriorityQueue<Evento>();
		agenti = new LinkedList<Agente>();
		rand = new Random();

		// ricevo i parametri
		this.partenza = partenza;

		this.eventiDaSimulare = eventi;

		// creo tutti gli agenti
		for (int i = 1; i <= N_AGENTI; i++) {
			Agente a = new Agente(i, partenza); // creo un agente libero = true

			agenti.add(a);

		}
		this.queue.clear();
		for (Event e : eventiDaSimulare) {

			Evento ev = new Evento(Evento.TIPO.INIZIO_CRIMINE, e, e.getReported_date());
			queue.add(ev);
		}

		// imposto lo stato iniziale

	}

	Model model = new Model();

	public void run() {
		// estraggo un evento alla volta

		while (!(queue.isEmpty())) {

			// eseguo l'evento
			Evento ev = queue.poll();

			switch (ev.getTipo()) {
			case INIZIO_CRIMINE:
				Event e = ev.getEvento();

				// cerco l'agente nel distretto più vicino
				Agente agente = scegliAgente(ev.getEvento().getDistrict_id());

				if (agente != null) {
					// vedo quanto tempo impiega

					double tempoImpiegato = 0;
					if (agente.getDistretto().equals(model.mappaDistretti.get(e.getDistrict_id()))) {
						tempoImpiegato = 0;

					} else {
						tempoImpiegato = (calcolaTempo(agente.getDistretto(), ev.getEvento().getDistrict_id()));
						if (tempoImpiegato > 15) {
							n_malGestiti++;
						}
					}
					agente.setEoccupato(true);
					agente.setDistretto(model.mappaDistretti.get(ev.getEvento().getDistrict_id()));

					// tempo
					if (ev.getEvento().getOffense_category_id().equals("all_other_crimes")) {
						tempoImpiegato += (rand.nextInt(2) + 1);
					} else
						tempoImpiegato += 2;

					queue.add(new Evento(Evento.TIPO.FINE_CRIMINE, agente,
							ev.getData().plusMinutes((long) (tempoImpiegato * 60))));
				} else
					this.n_malGestiti++;
				break;

			case FINE_CRIMINE:
				// agente libero
				System.out.println("Crimine gestito " + ev.getTipo());
				ev.getAgente().setEoccupato(false);
				break;
			}
		}
	}

	private double calcolaTempo(Distretto distrettoAgente, Integer district_idEvento) {
		double distanza = 0;

		LatLng punto1 = new LatLng(distrettoAgente.getLat(), distrettoAgente.getLon());
		Distretto d2 = model.mappaDistretti.get(district_idEvento);
		LatLng punto2 = new LatLng(d2.getLat(), d2.getLon());

		if (distrettoAgente != d2)
			// return grafo.getEdgeWeight(grafo.getEdge(distrettoAgente, d2));
			// return grafo.getEdge(sourceVertex, targetVertex)
			distanza = LatLngTool.distance(punto1, punto2, LengthUnit.KILOMETER);
		return distanza;
	}

	private Agente scegliAgente(Integer district_id) {
		double distanzaMin;
		distanzaMin = Double.MAX_VALUE;

		// List<Agente> agentiLiberi = new ArrayList<Agente>();
		Agente scelto =null;
		Distretto d2 = model.mappaDistretti.get(district_id);
		
		for (Agente a : agenti) {
			if (a.getDistretto().equals(d2) && !a.isEoccupato()) {
				return a;
			} else {
				if (this.calcolaTempo(a.getDistretto(), district_id) <= distanzaMin) {
					if (!a.isEoccupato()) {
						distanzaMin = this.calcolaTempo(a.getDistretto(), district_id);
						scelto = a;
					}
				}
			}

		}
		
			return scelto;
	}

	
	/* Collections.sort(agentiLiberi, new Comparator<Agente>() {
	  
	  @Override public int compare(Agente a1, Agente a2) { if (a1.getDistretto() ==
	  d2) return -1; if (a2.getDistretto() == d2) { return -1;
	  
	  }
	  
	  return (int) (grafo.getEdgeWeight(grafo.getEdge(d2, a1.getDistretto())) -
	  grafo.getEdgeWeight(grafo.getEdge(d2, a2.getDistretto())));
	  
	  } });*/

	public int getEventiMalGestiti() {

		return n_malGestiti;
	}

}
