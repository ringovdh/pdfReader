package be.yorian.main;

import be.yorian.gui.ScreenManager;
import be.yorian.persistence.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stage parentWindow;

	public static void main(String... args) {
		
		Application.launch(Main.class, args);
	
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		parentWindow = stage;
		
		// Domeincontroller instantie.
        DomeinController domeinController = new DomeinController();
        
        parentWindow.setTitle("Beheer uw budget!");
        
        ScreenManager screenManager = null;
        try {
        	screenManager = new ScreenManager(domeinController);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        Scene scene = new Scene(screenManager.getFrame());
        scene.getStylesheets().add("/styles.css");
        parentWindow.setScene(scene);

        parentWindow.show();
		
	}
	
	@Override
	public void stop() {
	    System.exit(0);
	}

}
