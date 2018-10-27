package be.yorian.gui;

import java.io.IOException;
import java.util.ArrayList;

import be.yorian.entities.Periode;
import be.yorian.persistence.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TransactiePerMaandFrameController extends SplitPane {

	private final DomeinController domeinController;
	private final ScreenManager screenManager;
	@FXML
	private Label messageLabel;
	@FXML
	private ChoiceBox<Periode> kiesMaand;
	@FXML
	private AnchorPane transactiePane;
	@FXML
	private GridPane overzichtPane;
	@FXML
	private GridPane verdelingPane;
	

	public TransactiePerMaandFrameController(DomeinController domeinController, Label messageLabel) {

		this.domeinController = domeinController;
		this.messageLabel = messageLabel;
		this.screenManager = new ScreenManager(domeinController);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieMaandDetail.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		vulKeuzeLijst();
		
	}

	
	
	public void toonTransacties() {
		
		overzichtPane = screenManager.switchToMaandOverzicht(kiesMaand.getValue().getId());
		transactiePane.getChildren().clear();
		transactiePane.getChildren().add(overzichtPane);
		
	}

	

	public void toonVerdeling() throws IOException {
		
		verdelingPane = screenManager.switchToMaandVerdeling(kiesMaand.getValue().getId());
		transactiePane.getChildren().clear();
		transactiePane.getChildren().add(verdelingPane);
		
	}
	
	
	
	private void vulKeuzeLijst() {

		ArrayList<Periode> dates = new ArrayList<>();
		for (Periode periode : domeinController.geefAllePeriodes()) {
			dates.add(periode);
		}
		ObservableList<Periode> list = FXCollections.observableArrayList(dates);
		kiesMaand.setItems(list);
		
	}

}
