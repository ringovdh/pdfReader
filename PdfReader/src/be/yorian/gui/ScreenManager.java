package be.yorian.gui;

import be.yorian.persistence.DomeinController;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class ScreenManager {

	private final BudgetAppFrameController budgetAppFrameController;
	private final TransactieVerdelingFrameController transactieVerdelingFrameController;
	private final TransactieOverzichtFrameController transactieOverzichtFrameController;
	
	public ScreenManager(DomeinController domeinController) {
		
		this.budgetAppFrameController = new BudgetAppFrameController(domeinController);
		this.transactieVerdelingFrameController = new TransactieVerdelingFrameController(domeinController);
		this.transactieOverzichtFrameController = new TransactieOverzichtFrameController(domeinController);
	}

	public Parent getFrame() {
		
		return budgetAppFrameController;
	}

	public GridPane switchToVerdeling(int id) {
		
		return transactieVerdelingFrameController.getPane();
	}

	public GridPane switchToOverzicht(int id) {
		
		return transactieOverzichtFrameController.toonResultaten(id);
	}
	


}
