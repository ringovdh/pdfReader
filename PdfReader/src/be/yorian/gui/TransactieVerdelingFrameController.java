package be.yorian.gui;

import java.io.IOException;

import be.yorian.persistence.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class TransactieVerdelingFrameController extends GridPane{

	private final DomeinController domeinController;
	
	@FXML
	private GridPane verdelingPane;
	
	
	public TransactieVerdelingFrameController(DomeinController domeinController) {

		this.domeinController = domeinController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactieVerdeling.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

	public GridPane getPane() {
		return this.verdelingPane;
		
	}
}
