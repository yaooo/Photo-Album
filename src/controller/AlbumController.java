package controller;
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	@FXML
	private TextField newAlbumText;
	
	private ObservableList<String> obsList = FXCollections.observableArrayList();
	User currentUser;
	private List<Album> albums;
	UserList u;
	public void start(Stage stage , User username) throws IOException, ClassNotFoundException {
		currentUser=username;
		albums=new ArrayList<Album>();
		albums=currentUser.getAlbums();
		updateDisplay();
		u=new UserList();
		u=UserList.read();
		stage.setOnCloseRequest( event -> {
    		try {
    			u.removeUser(currentUser.getName());
    			u.addUser(currentUser);
    			u.write(u);}
    		catch(IOException e){
    			e.printStackTrace();
    		}
    } );
	}
	
	@FXML protected void handleExitButton(ActionEvent event) throws ClassNotFoundException{
		Parent parent;
		 try {
			 		u.removeUser(currentUser.getName());
					u.addUser(currentUser);
			 		u.write(u);
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
	
	@FXML protected void handleCreateButton(ActionEvent event) {
		String s= newAlbumText.getText();
		currentUser.addAlbum(s);
		albums=currentUser.getAlbums();
		updateDisplay();
	}
	@FXML protected void handleDeleteButton(ActionEvent event) {
		String s =(String) listAlbum.getSelectionModel().getSelectedItem();
		Album delete=currentUser.getAlbumByName(s);
		currentUser.removeAlbum(delete);
		albums=currentUser.getAlbums();
		updateDisplay();
	}
	@FXML protected void handleRenameButton(ActionEvent event) {
		String s =(String) listAlbum.getSelectionModel().getSelectedItem();
		currentUser.getAlbumByName(s).setName(newAlbumText.getText());
		updateDisplay();
	}
	public void updateDisplay() {
		obsList.clear();
		for(Album a:albums) {
			obsList.add(a.getName());
		}
		listAlbum.setItems(obsList);
	}
}
