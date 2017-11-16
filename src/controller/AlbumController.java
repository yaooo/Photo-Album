package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.application.*;

public class AlbumController {
	@FXML
	private ListView listAlbum;
	@FXML
	private Button DeleteAlbum;
	@FXML
	private Button RenameAlbum;
	@FXML
	private Button OpenAlbum;
	@FXML
	private Button Exit;
	@FXML
	private Button CreateAlbum;

	public void start(Stage stage) throws IOException {
		
	}
	
	@FXML protected void handleExitButton(ActionEvent event) throws ClassNotFoundException{
		Parent parent;
		 try {
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
					parent = (Parent) loader.load();
					        
					LoginController ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
		        
		 }catch (IOException e) {
		    	e.printStackTrace();
		    }
	}
}
