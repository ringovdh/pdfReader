package be.yorian.gui;

import be.yorian.persistence.DomeinController;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class ScreenManager {

	private final BudgetAppFrameController budgetAppFrameController;
	private final TransactieVerdelingFrameController transactieVerdelingFrameController;
	private final TransactieMaandOverzichtFrameController transactieMaandOverzichtFrameController;
	private final TransactieTypeOverzichtFrameController transactieTypeOverzichtFrameController;
	
	public ScreenManager(DomeinController domeinController) {
		
		this.budgetAppFrameController = new BudgetAppFrameController(domeinController);
		this.transactieVerdelingFrameController = new TransactieVerdelingFrameController(domeinController);
		this.transactieMaandOverzichtFrameController = new TransactieMaandOverzichtFrameController(domeinController);
		this.transactieTypeOverzichtFrameController = new TransactieTypeOverzichtFrameController(domeinController);
	}

	public Parent getFrame() {
		
		return budgetAppFrameController;
	}

	public GridPane switchToMaandVerdeling(int id) {
		
		return transactieVerdelingFrameController.toonVerdeling(id);
	}

	public GridPane switchToMaandOverzicht(int id) {
		
		return transactieMaandOverzichtFrameController.toonResultaten(id);
	}
	
	public GridPane switchToTypeOverzicht(String type) {
		
		return transactieTypeOverzichtFrameController.toonResultaten(type);
	}

}
