package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import controller.LoginController;;


public class Main extends Application {
	Stage mainStage;
	@Override
	public void start(Stage stage) {
		mainStage = stage;
		try {
				
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/Login.fxml"));
			BorderPane root =(BorderPane) loader.load();
			Scene scene = new Scene(root);
			
			mainStage.setScene(scene);
			mainStage.setTitle("Song Library");
			mainStage.setResizable(false);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
