package controller;

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

public class LoginController {
	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	public void start(Stage stage) throws IOException {
		
	}
	public void handleLoginButtonAction() throws IOException {
		System.out.println("login successful");
		
	}

    @FXML protected void handleLoginButtonAction(ActionEvent event) throws ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Parent parent;
       
        try {
	        if(username.equals("admin")) {
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
				parent = (Parent) loader.load();
				        
				AlbumController ctrl = loader.getController();
				Scene scene = new Scene(parent);
							
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
			                
				ctrl.start(app_stage);
			             
			    app_stage.setScene(scene);
			    app_stage.show();  
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
        	
    }
    
    



	
}
