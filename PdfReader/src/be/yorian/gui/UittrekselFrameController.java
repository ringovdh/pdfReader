package be.yorian.gui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import be.yorian.entities.Categorie;
import be.yorian.entities.Periode;
import be.yorian.entities.Transactie;
import be.yorian.main.MyPDFReader;
import be.yorian.persistence.DomeinController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class UittrekselFrameController extends SplitPane {

	private final DomeinController domeinController;
	private final FileChooser fileChooser = new FileChooser();
	@FXML
	private ListView<String> overzichtTX;
	@FXML
	private Label aantalTX;
	@FXML
	private Label txBestand;
	@FXML
	private Label txNummer;
	@FXML
	private Label txDatum;
	@FXML
	private ChoiceBox<Periode> txPeriode;
	@FXML
	private TextArea txOmschrijving;
	@FXML
	private Label txTeken;
	@FXML
	private Label txBedrag;
	@FXML
	private ChoiceBox<Categorie> txCategorie;
	@FXML
	private Button bewaarBtn;
	@FXML
	private Label messageLabel;
	
	private ObservableList<Transactie> txlist;

	public UittrekselFrameController(DomeinController domeinController, Label messageLabel) {

		this.domeinController = domeinController;
		this.messageLabel = messageLabel;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BestandLezen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void leesBestand() {
		try {
			ListView<String> transacties = selecteerBestand("Importeer uittreksels");

			if (transacties != null) {
				messageLabel.setText("De transacties werden succesvol geimporteerd.");
				
			}
		} catch (Exception ex) {
			new Alert(Alert.AlertType.ERROR, "Er ging iets mis met het inlezen van de transacties.").showAndWait();
		}
		txCategorie.getItems().setAll(Categorie.values());
		txPeriode.getItems().setAll(domeinController.geefAllePeriodes());
		handleItemClicks();
	}

	private void configureFileChooser(FileChooser fileChooser, String titel) {
		
		fileChooser.setTitle(titel);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

	}

	private ListView<String> selecteerBestand(String titel) throws IOException, ParseException {

		configureFileChooser(fileChooser, titel);

		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {

			MyPDFReader reader = new MyPDFReader();
			reader.zetOmNaarText(file);
			
			txlist = reader.filterTransacties();
			aantalTX.setText(Integer.toString(txlist.size()));
			txBestand.setText(file.getName());
			for (Transactie tx : txlist) {
				overzichtTX.getItems().add("transactie: " + tx.getTxnummer());
			}
//			txCategorie.getItems().setAll(Categorie.values());
//			txPeriode.getItems().setAll(domeinController.geefAllePeriodes());
//			handleItemClicks();
		}

		return overzichtTX;
	}

	private void handleItemClicks()
    {
		overzichtTX.setOnMouseClicked(event -> {
            int selectedIndex = overzichtTX.getSelectionModel().getSelectedIndex();
            bewerkTX(txlist.get(selectedIndex));
        });
    }
	
	private void bewerkTX(Transactie tx) {
		txNummer.setText(tx.getTxnummer());
		txDatum.setText(tx.getDatum().toString());
		txOmschrijving.setText(tx.getOmschrijving());
		txTeken.setText(tx.getTeken());
		txBedrag.setText(tx.getBedrag().toString());
		bewaarBtn.setOnAction(event ->{
			bewaarTransactie(tx);
		});
	}
	
	public void bewaarTransactie(Transactie tx) {
		tx.setPeriode(txPeriode.getValue().getId());
		tx.setOmschrijving(txOmschrijving.getText());
		tx.setCategorie(txCategorie.getValue().getCategorie());
		domeinController.bewaarTransactie(tx);
		messageLabel.setText("Transactie " + tx.getTxnummer() + " is bewaard");
	}

}
