package be.yorian.gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import be.yorian.entities.Transactie;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

@SuppressWarnings("rawtypes")
public class TransactieTypeOverzichtFrameController extends GridPane {

	private final DomeinController domeinController;

	@FXML
	private GridPane overzichtPane;
	@FXML
	private TableView<Transactie> TXTable;
	@FXML
	private TableColumn kolomTx;
	@FXML
	private TableColumn kolomDatum;
	@FXML
	private TableColumn kolomOmschrijving;
	@FXML
	private TableColumn kolomBedrag;
	@FXML
	private TableColumn kolomTeken;
	@FXML
	private TableColumn kolomCategorie;
	@FXML
	private LineChart<String, BigDecimal> typeGrafiek; 
	@FXML
	private CategoryAxis xAs;
	@FXML
	private NumberAxis yAs;
	
	
	public TransactieTypeOverzichtFrameController(DomeinController domeinController) {

		this.domeinController = domeinController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieTypeOverzicht.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	
	
	public GridPane toonResultaten(String type) {

		// Maak eerst het overzichtPane terug leeg
		if (typeGrafiek.getData().size() == 1) {
			typeGrafiek.getData().remove(0);
		}
		
		// Haal tx per type op
		ArrayList<Transactie> transactiesPerType = domeinController.geefTransactiesPerType(type);
		
		// Maak het overzichtscherm
		ObservableList<Transactie> resultaten = geefResultatenPerMaand(transactiesPerType);
		TXTable.setItems(resultaten);
		
		// Bereken maandtotaal
		Map<Integer, BigDecimal> totaalPerMaand = berekenTotaalPerMaand(transactiesPerType);

		// Maak diagram
		Series<String, BigDecimal> series = maakDiagram(totaalPerMaand);
		series.setName(type);
		typeGrafiek.setTitle("Jaaroverzicht: " + type);
		typeGrafiek.getData().add(series);
		
		return this.overzichtPane;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private ObservableList<Transactie> geefResultatenPerMaand(ArrayList<Transactie> transactiesPerType) {
		
		ObservableList<Transactie> resultaten = FXCollections.observableArrayList(transactiesPerType);
		
		kolomTx.setCellValueFactory(new PropertyValueFactory<>("txnummer"));
		kolomDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		kolomOmschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));
		kolomBedrag.setCellValueFactory(new PropertyValueFactory<>("bedrag"));
		kolomTeken.setCellValueFactory(new PropertyValueFactory<>("teken"));
		kolomCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		
		return resultaten;

	}
	
	private Map<Integer, BigDecimal> berekenTotaalPerMaand(ArrayList<Transactie> transactiesPerType) {
		
		Map<Integer, BigDecimal> resultaten = new HashMap<>();
		
		for(Transactie tx : transactiesPerType) {
			if(resultaten.containsKey(tx.getPeriode())){
				resultaten.put(tx.getPeriode(), resultaten.get(tx.getPeriode()).add(tx.getBedrag()));
			}
			else {
				resultaten.put(tx.getPeriode(), tx.getBedrag());
			}
		}

		return resultaten;
		
	}
	
	private XYChart.Series<String, BigDecimal> maakDiagram(Map<Integer, BigDecimal> totaalPerMaand) {
		
		XYChart.Series<String, BigDecimal> serie = new XYChart.Series<String, BigDecimal>();
		
		SortedSet<Integer> keySet = new TreeSet<>(totaalPerMaand.keySet());
		
		for( Integer key : keySet) {
			serie.getData().add(new XYChart.Data<String, BigDecimal>(String.valueOf(key), totaalPerMaand.get(key)));
		}
		
		return serie;
		
	}

}
