package be.yorian.gui;

import java.math.BigDecimal;
import java.util.Optional;

import be.yorian.entities.Categorie;
import be.yorian.entities.Transactie;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class TransactieMaandOverzichtFrameController extends GridPane {

	private final DomeinController domeinController;
	private final CommonFrameController frameControllerHelper;
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
	private TableColumn kolomCategorie;
	@FXML
	private TableColumn nkolomCategorie;

	
	public TransactieMaandOverzichtFrameController(DomeinController domeinController) {

		this.domeinController = domeinController;
		this.frameControllerHelper = new CommonFrameController();

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

		// behandel negatieve transacties
		ObservableList<Transactie> negatieveResultaten = geefNegatieveTransacties(id);
		negatieveTXTable.setItems(negatieveResultaten);
		BigDecimal somNegatief = frameControllerHelper.berekenTotaal(negatieveResultaten);
		totaalNegatief.setText(somNegatief.toString());
		negatieveItems.setText(Integer.toString(negatieveResultaten.size()) + " transacties");

		// behandel positieve transacties
		ObservableList<Transactie> positieveResultaten = geefPositieveTransacties(id);
		positieveTXTable.setItems(positieveResultaten);
		BigDecimal somPositief = frameControllerHelper.berekenTotaal(positieveResultaten);
		totaalPositief.setText(somPositief.toString());
		positieveItems.setText(Integer.toString(positieveResultaten.size()) + " transacties");

		// bereken totaal
		BigDecimal somSaldo = somPositief.subtract(somNegatief);
		totaalSaldo.setText(somSaldo.toString());

		// Maak transacties aanklikbaar om te wijzigen 
		handlePositieveItemClicks(positieveResultaten);
		handleNegatieveItemClicks(negatieveResultaten);

		return this.overzichtPane;
	}

	// private methods
	private void handlePositieveItemClicks(ObservableList<Transactie> positieveResultaten) {
		positieveTXTable.setOnMouseClicked(event -> {
			int selectedIndex = positieveTXTable.getSelectionModel().getSelectedIndex();
			Transactie tx = bewerkTX(positieveResultaten.get(selectedIndex));
			positieveResultaten.set(selectedIndex, tx);
		});
	}
	
	private void handleNegatieveItemClicks(ObservableList<Transactie> negatieveResultaten) {
		
		negatieveTXTable.setOnMouseClicked(event -> {
			int selectedIndex = negatieveTXTable.getSelectionModel().getSelectedIndex();
			Transactie tx = bewerkTX(negatieveResultaten.get(selectedIndex));
			negatieveResultaten.set(selectedIndex, tx);
		});
	}

	private Transactie bewerkTX(Transactie transactie) {
		
		Optional<Transactie> result = toonDialoog(transactie);
		if (result.isPresent()){
			transactie = result.get();
			domeinController.bewaarTransactie(transactie);
		}
		
		return transactie;
	}

	private Optional<Transactie> toonDialoog(Transactie tx) {

		Dialog<Transactie> dialog = new Dialog<>();
		dialog.setTitle("Transactie bewerken.");
		dialog.setHeaderText("Transactie " + tx.getTxnummer() + " bewerken.");

		ButtonType bewaarKnop = new ButtonType("Bewaar", ButtonData.OK_DONE);
		ButtonType annuleerKnop = new ButtonType("Annuleer",ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(bewaarKnop, annuleerKnop);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		Label transactieNr = new Label();
		transactieNr.setText(tx.getTxnummer());
		Label datum = new Label();
		datum.setText(tx.getDatum());
		TextArea beschrijving = new TextArea();
		beschrijving.setText(tx.getOmschrijving());
		Label bedrag = new Label();
		bedrag.setText(tx.getTeken() + " " + tx.getBedrag().toString());		
		ChoiceBox<Categorie> txCategorie = new ChoiceBox<>();
		txCategorie.getItems().setAll(Categorie.values());
		txCategorie.setValue(Categorie.valueOf(tx.getCategorie().toUpperCase()));
		
		grid.add(new Label("Transactie:"), 0, 0);
		grid.add(transactieNr, 1, 0);
		grid.add(new Label("Datum:"), 0, 1);
		grid.add(datum, 1, 1);
		grid.add(new Label("Beschrijving:"), 0, 2);
		grid.add(beschrijving, 1, 2);
		grid.add(new Label("Bedrag:"), 0, 3);
		grid.add(bedrag, 1, 3);
		grid.add(new Label("Categorie:"), 0, 4);
		grid.add(txCategorie, 1, 4);
		
		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(new Callback<ButtonType, Transactie>() {
			@Override
			public Transactie call(ButtonType b) {
				if (b == bewaarKnop) {
					tx.setOmschrijving(beschrijving.getText());
					tx.setCategorie(txCategorie.getValue().getCategorie());
					
					return tx;
				}
				return null;
			}
		});

		return dialog.showAndWait();
	}

	@SuppressWarnings("unchecked")
	private ObservableList<Transactie> geefNegatieveTransacties(int periode) {

		ObservableList<Transactie> negatieveResultaten = FXCollections
				.observableArrayList(domeinController.geefNegatieveTransactiesPerMaand(periode));
		nkolomTx.setCellValueFactory(new PropertyValueFactory<>("txnummer"));
		nkolomDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
		nkolomOmschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));
		nkolomBedrag.setCellValueFactory(new PropertyValueFactory<>("bedrag"));
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
		kolomCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

		return positieveResultaten;

	}

}
