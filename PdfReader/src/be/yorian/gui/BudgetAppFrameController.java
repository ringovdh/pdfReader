package be.yorian.gui;

import java.io.IOException;

import be.yorian.entities.Spaarpot;
import be.yorian.persistence.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class BudgetAppFrameController extends BorderPane {

	private final DomeinController domeinController;
	private final Spaarpot spaarpot;
	
	@FXML
    private Button tabBestandLezen;
	@FXML
	private Button toonTransactiesPerMaand;
	@FXML
	private Button toonTransactiesPerType;
	@FXML
	private Button toonVerdeling;
	@FXML
    private Label messageLabel;
	@FXML
    private Label zichtRekening;
	@FXML
	private Label spaarRekening;
	@FXML
	private ImageView spaarIV;
	@FXML
	private ImageView zichtIV;
	
	
	public BudgetAppFrameController(DomeinController domeinController) {
		this.domeinController = domeinController;
		Image spaarRekeningIcon = new Image("File:resources/images/spaarVarken.png");
		Image zichtRekeningIcon = new Image("File:resources/images/wallet.png");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
        	ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        this.spaarpot = new Spaarpot(domeinController, zichtRekening, spaarRekening);
        zichtIV.setImage(zichtRekeningIcon);
        spaarIV.setImage(spaarRekeningIcon);
        spaarpot.berekenZichtRekening();
        spaarpot.berekenSpaarRekening();
	}


	public void switchUittrekselFrame() {
		UittrekselFrameController uittrekselFrameController = new UittrekselFrameController(domeinController, messageLabel, spaarpot);
		messageLabel.setText("Uittreksels inlezen");
		this.setCenter(uittrekselFrameController);
	}
	
	public void switchToMaandOverzicht() {
		TransactiePerMaandFrameController transactiePerMaandFrameController =  new TransactiePerMaandFrameController(domeinController, messageLabel);
		messageLabel.setText("Bekijk een overzicht per maand");
		this.setCenter(transactiePerMaandFrameController);
	}
	
	public void switchToTypeOverzicht() {
		TransactiePerTypeFrameController transactiePerTypeFrameController = new TransactiePerTypeFrameController(domeinController, messageLabel);
		messageLabel.setText("Bekijk een overzicht per categorie");
		this.setCenter(transactiePerTypeFrameController);
	}
}
