package be.yorian.gui;

import java.io.IOException;
import java.util.ArrayList;

import be.yorian.entities.Categorie;
import be.yorian.entities.Omschrijving;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BeheerOmschrijvingFrameController extends GridPane{
	
	private final DomeinController domeinController;
	private final CommonFrameController frameControllerHelper;
	@FXML
	private Label messageLabel;
	@FXML
	private TextField txNieuweZoekterm;
	@FXML
	private TextArea txOmschrijving;
	@FXML
	private ChoiceBox<Categorie> txCategorie;
	@FXML
	private ChoiceBox<String> txZoekterm;
	@FXML
	private Button bewaarBtn;
	@FXML
	private Button bewerkBtn;
	
	public BeheerOmschrijvingFrameController(DomeinController domeinController, Label messageLabel) {
		this.domeinController = domeinController;
		this.frameControllerHelper = new CommonFrameController();
		this.messageLabel = messageLabel;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BeheerOmschrijving.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		zoektermKeuzeLijstVoorbereiden();
		txCategorie.setItems(frameControllerHelper.vulCategorieLijst());
		
	}
	
	
	public void bewaarZoekterm() {
		
		if (valideerNieuweZoekterm()) {
			Omschrijving omschrijving = new Omschrijving(txNieuweZoekterm.getText(), Categorie.valueOf("ONBEPAALD").name());
			domeinController.bewaarZoekterm(omschrijving);
			txZoekterm.getItems().add(txNieuweZoekterm.getText());
			messageLabel.setText("nieuwe zoekterm: " + txNieuweZoekterm.getText() +" is bewaard");
			txNieuweZoekterm.clear();
		}
		else {
			messageLabel.setText("nieuwe zoekterm: " + txNieuweZoekterm.getText() +" kon niet bewaard worden!");
		}
	}
	

	public void bewerkZoekterm() {
		
		if (valideerBestaandeZoekterm()) {
			Omschrijving omschrijving = domeinController.geefOmschrijving(txZoekterm.getValue());
			omschrijving.setOmschrijving(txOmschrijving.getText());
			omschrijving.setCategorie(txCategorie.getValue().name());
			domeinController.bewaarZoekterm(omschrijving);
			messageLabel.setText("Omschrijving: " + txZoekterm.getValue() +" is bewaard");
		}
		else {
			messageLabel.setText("De zoekterm kon niet bewaard worden!");
		}
	}


	// private methodes
	private void zoektermKeuzeLijstVoorbereiden() {

		ArrayList<String> zoektermen = domeinController.geefAlleZoektermen();
		txZoekterm.setItems(FXCollections.observableArrayList(zoektermen));

        txZoekterm.setOnAction((event) -> {
        	Omschrijving omschrijving = domeinController.geefOmschrijving(txZoekterm.getSelectionModel().getSelectedItem());
        	txOmschrijving.setText(omschrijving.getOmschrijving());
        	txCategorie.setValue(Categorie.valueOf(omschrijving.getCategorie()));
        	
        });
	}
	
	
	private boolean valideerNieuweZoekterm() {
		
		boolean isGevalideerd = true;
		String alertTekst = "";
		if (txNieuweZoekterm.getText().isEmpty()) {
			isGevalideerd = false;
			alertTekst += "U hebt geen zoekterm ingegeven! \n";
		}
		if (domeinController.geefOmschrijving(txNieuweZoekterm.getText()) != null) {
			isGevalideerd = false;
			alertTekst += "Deze zoekterm bestaat al!";
		}
		if (!isGevalideerd) {
			new Alert(Alert.AlertType.ERROR, alertTekst).showAndWait();
		}
		
		return isGevalideerd;
	}
	
	
	private boolean valideerBestaandeZoekterm() {
		
		boolean isGevalideerd = true;
		String alertTekst = "";
		if (txZoekterm.getValue() == null) {
			isGevalideerd = false;
			alertTekst += "U hebt geen zoekterm ingegeven! \n";
		}
		if (txOmschrijving.getText()== null) {
			isGevalideerd = false;
			alertTekst += "U hebt geen omschrijving ingegeven! \n";
		}
		if (txCategorie.getValue().name() == "ONBEPAALD") {
			isGevalideerd = false;
			alertTekst += "U hebt geen categorie ingegeven!";
		}
		if (!isGevalideerd) {
			new Alert(Alert.AlertType.ERROR, alertTekst).showAndWait();
		}
				
		return isGevalideerd;
	}

}
