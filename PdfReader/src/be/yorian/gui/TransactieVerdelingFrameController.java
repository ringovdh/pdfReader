package be.yorian.gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.yorian.entities.Transactie;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;

public class TransactieVerdelingFrameController extends GridPane{

	private final DomeinController domeinController;
	
	@FXML
	private GridPane verdelingPane;
	@FXML
	private PieChart verdelingTaart;
	
	
	public TransactieVerdelingFrameController(DomeinController domeinController) {

		this.domeinController = domeinController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieMaandVerdeling.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}


	public GridPane toonVerdeling(int id) {
		
		// Maak eerst het verdelingsPane terug leeg
		verdelingPane.getChildren().clear();
		
		// Haal alle positieve transacties op
		ArrayList<Transactie> transacties = domeinController.geefNegatieveTransactiesPerMaand(id);
		ObservableList<PieChart.Data> resultatenLijst = maakLijst(transacties);
		
		PieChart verdelingTaart = new PieChart();
		verdelingTaart.setData(resultatenLijst);
		verdelingTaart.setTitle("Uitgaven"); 
		
		// voeg nieuwe taart toe
		verdelingPane.getChildren().add(verdelingTaart);
		
		return this.verdelingPane;
	
	}


	private ObservableList<PieChart.Data> maakLijst(ArrayList<Transactie> transacties) {
		
		Map<String,BigDecimal> multiMap = new HashMap<>();
		for(Transactie tx : transacties) {
			if(multiMap.containsKey(tx.getCategorie())){
				multiMap.put(tx.getCategorie(), multiMap.get(tx.getCategorie()).add(tx.getBedrag()));
			}
			else {
				multiMap.put(tx.getCategorie(), tx.getBedrag());
			}
		}
		
		Set<String> keySet = multiMap.keySet();
		ObservableList<PieChart.Data> resultatenLijst = FXCollections.observableArrayList();
		for(String key : keySet) {
			resultatenLijst.add(new PieChart.Data(key, multiMap.get(key).doubleValue()));
		}

		return resultatenLijst;
	}
}
