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

public class TransactieFrameController extends SplitPane {

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
	

	public TransactieFrameController(DomeinController domeinController, Label messageLabel) {

		this.domeinController = domeinController;
		this.messageLabel = messageLabel;
		this.screenManager = new ScreenManager(domeinController);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieDetail.fxml"));
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
		
		overzichtPane = screenManager.switchToOverzicht(kiesMaand.getValue().getId());
		transactiePane.getChildren().remove(verdelingPane);
		transactiePane.getChildren().add(overzichtPane);
		
	}

	

	public void toonVerdeling() throws IOException {
		
		verdelingPane = screenManager.switchToVerdeling(kiesMaand.getValue().getId());
		transactiePane.getChildren().remove(overzichtPane);
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
