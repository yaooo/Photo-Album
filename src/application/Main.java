package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import controller.LoginController;;


public class Main extends Application {
	LoginController master;
	@Override
	public void start(Stage stage) {
		try {
				
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/View/Login.fxml"));
			BorderPane root = loader.load();
			master = loader.getController();
			master.start(stage);
			Scene scene = new Scene(root);

			stage.setTitle("Song Library");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
