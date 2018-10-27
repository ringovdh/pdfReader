package be.yorian.gui;

import java.io.IOException;
import java.math.BigDecimal;

import be.yorian.entities.Transactie;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

@SuppressWarnings("rawtypes")
public class TransactieMaandOverzichtFrameController extends GridPane {

	private final DomeinController domeinController;

	@FXML
	private GridPane overzichtPane;
	@FXML
	private TableView<Transactie> positieveTXTable;
	@FXML
	private TableView<Transactie> negatieveTXTable;
	@FXML
	private Label positieveItems;
	@FXML
	private Label negatieveItems;
	@FXML
	private Label totaalPositief;
	@FXML
	private Label totaalNegatief;
	@FXML
	private Label totaalSaldo;
	@FXML
	private TableColumn nkolomTx;
	@FXML
	private TableColumn kolomTx;
	@FXML
	private TableColumn kolomDatum;
	@FXML
	private TableColumn nkolomDatum;
	@FXML
	private TableColumn kolomOmschrijving;
	@FXML
	private TableColumn nkolomOmschrijving;
	@FXML
	private TableColumn kolomBedrag;
	@FXML
	private TableColumn nkolomBedrag;
	@FXML
	private TableColumn kolomTeken;
	@FXML
	private TableColumn nkolomTeken;
	@FXML
	private TableColumn kolomCategorie;
	@FXML
	private TableColumn nkolomCategorie;

	
	
	public TransactieMaandOverzichtFrameController(DomeinController domeinController) {

		this.domeinController = domeinController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieMaandOverzicht.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	
	
	public GridPane toonResultaten(int id) {

		// Maak eerst het overzichtPane terug leeg
		//overzichtPane.getChildren().clear();
		
		// behandel negatieve gedeelte
		ObservableList<Transactie> negatieveResultaten = geefNegatieveTransacties(id);
		negatieveTXTable.setItems(negatieveResultaten);
		BigDecimal somNegatief = berekenTotaal(negatieveResultaten);
		totaalNegatief.setText(somNegatief.toString());
		negatieveItems.setText(Integer.toString(negatieveResultaten.size()) + " transacties");

		// behandel positieve gedeelte
		ObservableList<Transactie> positieveResultaten = geefPositieveTransacties(id);
		positieveTXTable.setItems(positieveResultaten);
		BigDecimal somPositief = berekenTotaal(positieveResultaten);
		totaalPositief.setText(somPositief.toString());
		positieveItems.setText(Integer.toString(positieveResultaten.size()) + " transacties");

		// behandel totaal
		BigDecimal somSaldo = somPositief.subtract(somNegatief);
		totaalSaldo.setText(somSaldo.toString());
		
		return this.overzichtPane;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private ObservableList<Transactie> geefNegatieveTransacties(int periode) {

		ObservableList<Transactie> negatieveResultaten = FXCollections
				.observableArrayList(domeinController.geefNegatieveTransactiesPerMaand(periode));
		nkolomTx.setCellValueFactory(new PropertyValueFactory<>("txnummer"));
		nkolomDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		nkolomOmschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));
		nkolomBedrag.setCellValueFactory(new PropertyValueFactory<>("bedrag"));
		nkolomTeken.setCellValueFactory(new PropertyValueFactory<>("teken"));
		nkolomCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

		return negatieveResultaten;

	}

	
	
	@SuppressWarnings("unchecked")
	private ObservableList<Transactie> geefPositieveTransacties(int periode) {

		ObservableList<Transactie> positieveResultaten = FXCollections
				.observableArrayList(domeinController.geefPositieveTransactiesPerMaand(periode));
		kolomTx.setCellValueFactory(new PropertyValueFactory<>("txnummer"));
		kolomDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		kolomOmschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));
		kolomBedrag.setCellValueFactory(new PropertyValueFactory<>("bedrag"));
		kolomTeken.setCellValueFactory(new PropertyValueFactory<>("teken"));
		kolomCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

		return positieveResultaten;

	}
	
	
	
	private BigDecimal berekenTotaal(ObservableList<Transactie> resultaten) {

		BigDecimal totaal = new BigDecimal(0);

		for (Transactie tx : resultaten) {
			totaal = totaal.add(tx.getBedrag());
		}

		return totaal;
	
	}

}
