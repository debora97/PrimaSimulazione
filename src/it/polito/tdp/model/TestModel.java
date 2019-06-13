package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		Model model= new Model();
		
		model.getEventi();
		model.getDistretto(2017);
		model.creaGrafo();
		System.out.print(model.scriviVicini());
		System.out.print(model.getDistrettoMenoCrimini(2016));
		
		System.out.print(model.simula(5, 2017, 2, 14));
	}

}
