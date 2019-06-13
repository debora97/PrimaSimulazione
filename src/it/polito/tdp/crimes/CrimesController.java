/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class CrimesController {

	private Model model = new Model();

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxAnno"
	private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

	@FXML // fx:id="boxMese"
	private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

	@FXML // fx:id="boxGiorno"
	private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaReteCittadina"
	private Button btnCreaReteCittadina; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML // fx:id="txtN"
	private TextField txtN; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCreaReteCittadina(ActionEvent event) {
		int data = boxAnno.getValue();
		if (data != 0) {
			txtResult.clear();
			model.getEventi();
			model.getDistretto(data);
			model.creaGrafo();
			txtResult.appendText(model.scriviVicini());
		} else
			txtResult.appendText("Selezionare un anno");

	}

	@FXML
	void doSimula(ActionEvent event) {
		int n = 0;
		int anno = 0;
		int mese = 0;
		int giorno = 0;

		try {
			n = Integer.parseInt(txtN.getText().trim());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			txtN.clear();
			return;
		}

		if (boxAnno.getValue() == null) {
			txtResult.setText("Devi selezionare un anno!");
			return;
		} else
			anno = boxAnno.getValue();

		if (n < 1 || n > 10) {
			txtResult.setText("Inserisci numero tra 1 e 10");
			return;
		}

		if (boxMese.getValue() == null) {
			txtResult.setText("Devi selezionare un mese!");
			return;
		} else
			mese = (int) boxMese.getValue();

		if (boxGiorno.getValue() == null) {
			txtResult.setText("Devi selezionare un giorno!");
			return;
		} else
			giorno = (int) boxGiorno.getValue();

		

		txtResult.setText("Numero di eventi mal gestiti: " + model.simula(n, anno, mese, giorno ));

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		boxAnno.getItems().addAll(model.getAnni());

		boxMese.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		if (boxMese.getValue() == null) {
			txtResult.appendText("Inserisci un mese");
		}

		/*
		 * if (boxMese.getValue()==11|| boxMese.getValue()==4 || boxMese.getValue()==6
		 * || boxMese.getValue()==9 ){ for (int giorno = 1; giorno <= 30; giorno++) {
		 * boxGiorno.getItems().add(giorno);
		 * 
		 * }
		 * 
		 * } if (boxMese.getValue()==1 || boxMese.getValue()==3 || boxMese.getValue()==5
		 * || boxMese.getValue()==7 || boxMese.getValue()==8 || boxMese.getValue()==10
		 * || boxMese.getValue()==12 ) { for (int giorno = 1; giorno <= 31; giorno++) {
		 * boxGiorno.getItems().add(giorno);
		 * 
		 * }
		 * 
		 * } if (boxMese.getValue()==2 ) { for (int giorno = 1; giorno <= 29; giorno++)
		 * { boxGiorno.getItems().add(giorno);
		 * 
		 * }
		 */
		for (int giorno = 1; giorno <= 31; giorno++) {
			boxGiorno.getItems().add(giorno);

		}

		// }
		if (boxMese.getValue() != null && boxGiorno != null) {
			if (Integer.parseInt(txtN.getText()) < 1 || Integer.parseInt(txtN.getText()) > 10) {
				txtResult.clear();
				txtResult.appendText(" Selezionare un N compreso tra 1 e 10");

			}
		}

	}

}
