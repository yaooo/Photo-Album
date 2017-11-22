package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class Photos extends Application {
	private Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Login.fxml"));
            BorderPane root = (BorderPane) loader.load();
            Scene scene = new Scene(root);

            mainStage.setScene(scene);
            mainStage.setTitle("Song Library");
            mainStage.setResizable(false);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);

    }

}
