package it.polito.tdp.db;

import java.time.LocalDate;

import it.polito.tdp.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		//for(Event e : dao.listAllEvents())
		//	System.out.println(e);
		
		//LocalDate data = LocalDate.of(2017, 2, 14);
		for(Event e : dao.listEventiDaConsiderare(2017, 2, 10))
			System.out.println(e);
	}

}
