package it.polito.tdp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	SimpleWeightedGraph <Distretto, DefaultWeightedEdge> grafo;

	private List<Event> eventi;
	private List<Distretto> listaDistretto;
	
	private Map<Integer, Distretto> mappaDistretti;
	EventsDao dao= new EventsDao();
	
	public Model() {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappaDistretti= new HashMap<Integer, Distretto>();
	}
	
	public List<Event> getEventi(){
		this.eventi= new LinkedList<Event>(dao.listAllEvents());
		return this.eventi;
	}
	public List<Integer> getAnni(){
		 List<Integer> date= new LinkedList<Integer>();
		date= dao.getAnni();
		return date;
	}
	
	
	public List<Distretto> getDistretto(Integer anno){
		this.listaDistretto= new LinkedList<Distretto>(dao.getCentro(anno));
		for(Distretto d : this.listaDistretto) {
			this.mappaDistretti.put(d.getId(), d);
		}
		return this.listaDistretto;
	}
	
	public void creaGrafo() {
		Graphs.addAllVertices(grafo, this.mappaDistretti.values());
		
		for(Distretto d1: this.listaDistretto) {
			for(Distretto d2: this.listaDistretto) {
				DefaultWeightedEdge edge = grafo.getEdge(d1, d2);
				
				if(!d1.equals(d2) && edge==null ) {
					LatLng punto1= new LatLng (d1.getLat(), d1.getLon());
					LatLng punto2= new LatLng (d2.getLat(), d2.getLon());
					double peso;
					peso= LatLngTool.distanceInRadians(punto1, punto2); 
					Graphs.addEdge(grafo, d1, d2, peso);
				}
			}
		}
		System.out.println("Grafo creato!");
		System.out.println("Vertici: " + grafo.vertexSet().size());
		System.out.println("Archi: " + grafo.edgeSet().size());
	}
	
	public List<Distretto> getVicini(Distretto d){
		List<Distretto> vicini = new LinkedList<>();
		
		vicini= Graphs.neighborListOf(grafo, d);
		Collections.sort(vicini, new Comparator<Distretto>() {

			@Override
			public int compare(Distretto o1, Distretto o2) {
				DefaultWeightedEdge e1 = grafo.getEdge(d, o1);
				DefaultWeightedEdge e2 = grafo.getEdge(d, o2);
				Double peso1 = grafo.getEdgeWeight(e1);
				Double peso2 = grafo.getEdgeWeight(e2);
				return peso1.compareTo(peso2);
			}
		});
		return vicini;
	}
	public String scriviVicini() {
		String s="";
		for (Distretto d: this.listaDistretto) {
			s+="\nDistretto "+d.getId()+" vicino a \n";
			List<Distretto> vicini = new LinkedList<>(this.getVicini(d));
			for(Distretto d1: vicini) {
				s+="\nDistretto "+d1.getId();
			}
		}
		return s;
	}
	
	
}
