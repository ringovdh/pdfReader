package be.yorian.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import be.yorian.persistence.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class BudgetAppFrameController extends BorderPane implements Initializable{

	private final DomeinController domeinController;
	
	@FXML
    private Button tabBestandLezen;
	@FXML
	private Button tabTransacties;
	@FXML
	private Button toonVerdeling;
	@FXML
    private Label messageLabel;

	
	
	public BudgetAppFrameController(DomeinController domeinController) {
		this.domeinController = domeinController;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void switchUittrekselFrame() {
		UittrekselFrameController uittrekselFrameController = new UittrekselFrameController(domeinController, messageLabel);
		this.setCenter(uittrekselFrameController);
	}
	
	public void switchTransactieFrame() {
		TransactieFrameController transactieFrameController =  new TransactieFrameController(domeinController, messageLabel);
		this.setCenter(transactieFrameController);
	}
}
