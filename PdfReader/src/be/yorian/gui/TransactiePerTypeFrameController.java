package be.yorian.gui;

import java.io.IOException;
import java.util.ArrayList;

import be.yorian.entities.Categorie;
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

public class TransactiePerTypeFrameController extends SplitPane {

	private final DomeinController domeinController;
	private final ScreenManager screenManager;
	@FXML
	private Label messageLabel;
	@FXML
	private ChoiceBox<Categorie> kiesType;
	@FXML
	private AnchorPane transactiePane;
	@FXML
	private GridPane overzichtPane;
	
	

	public TransactiePerTypeFrameController(DomeinController domeinController, Label messageLabel) {

		this.domeinController = domeinController;
		this.messageLabel = messageLabel;
		this.screenManager = new ScreenManager(domeinController);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieTypeDetail.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		vulKeuzeLijst();
		
	}

	
	
	public void toonTransacties() {
		
		overzichtPane = screenManager.switchToTypeOverzicht(kiesType.getValue().getCategorie());
		transactiePane.getChildren().clear();
		transactiePane.getChildren().add(overzichtPane);
		
	}
	
	
	
	private void vulKeuzeLijst() {

		ArrayList<Categorie> types = new ArrayList<>();
		for (Categorie type : Categorie.values()) {
			types.add(type);
		}
		ObservableList<Categorie> list = FXCollections.observableArrayList(types);
		kiesType.setItems(list);
		
	}

}
