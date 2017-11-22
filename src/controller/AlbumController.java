package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.UserList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumController {
	@FXML
	private ListView listAlbum;
//	@FXML
//	private Button DeleteAlbum;
//	@FXML
//	private Button RenameAlbum;
//	@FXML
//	private Button OpenAlbum;
//	@FXML
//	private Button Exit;
//	@FXML
//	private Button search;
//	@FXML
//	private Button CreateAlbum;

	private ObservableList<String> obsList = FXCollections.observableArrayList();
	private User currentUser;
	private List<Album> albums;
	private UserList u;
	public void start(Stage stage , User username) throws IOException, ClassNotFoundException {
		currentUser=username;
		albums=new ArrayList<Album>();
		albums=currentUser.getAlbums();
		updateDisplay();
		u=new UserList();
		u=UserList.read();
		listAlbum.getSelectionModel().select(0);
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Album");
        dialog.setHeaderText("Create a new album?");
        dialog.setContentText("Please the name:");

        String s = "";
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            s = result.get();
        }else{
            return;
        }

		if(s.trim().length() == 0){
		    alert("Error", "Invalid Name", "Please try again.");
		    return;
        }
        if(checkForDuplicate(s)){
            alert("Error", "Duplicated Name", "Please try again.");
            return;
        }
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
	    int index = listAlbum.getSelectionModel().getSelectedIndex();
	    if(albums.size() < 1)
	        return;
        if(index == -1) {
            alert("Error", "Invalid Selection.", "Please select an item.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog((String) listAlbum.getSelectionModel().getSelectedItem());
        dialog.setTitle("Rename the Album");
        dialog.setHeaderText("Rename?");
        dialog.setContentText("Please the name:");

        String s;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            s = result.get();
        }else{
            return;
        }
        if(s.trim().length() == 0){
            alert("Error", "Invalid Name", "Please try again.");
            return;
        }
        if(checkForDuplicate(s)){
            alert("Error", "Duplicated Name", "Please try again.");
            return;
        }
        currentUser.getAlbumByName((String) listAlbum.getSelectionModel().getSelectedItem()).setName(s);
		updateDisplay();
	}
	@FXML protected void handleOpenButton(ActionEvent event) throws ClassNotFoundException {
		Parent parent;
        int index = listAlbum.getSelectionModel().getSelectedIndex();
        if(albums.size() < 1)
            return;
        if(index == -1) {
            alert("Error", "Invalid Selection.", "Please select an item.");
            return;
        }

		try {
			 		u.removeUser(currentUser.getName());
					u.addUser(currentUser);
			 		u.write(u);
			 		String s =(String) listAlbum.getSelectionModel().getSelectedItem();
			 		Album a =currentUser.getAlbumByName(s);
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PhotoList.fxml"));
					parent = (Parent) loader.load();
					        
					PhotoListController ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage,currentUser,a);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
		        
		 }catch (IOException e) {
		    	e.printStackTrace();
		    }
		
	}
	@FXML protected void handleSearchButton(ActionEvent event) throws ClassNotFoundException {
		Parent parent;
		 try {
			 		u.removeUser(currentUser.getName());
					u.addUser(currentUser);
			 		u.write(u);
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Search.fxml"));
					parent = (Parent) loader.load();
					        
					SearchController ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage,currentUser);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
		        
		 }catch (IOException e) {
		    	e.printStackTrace();
		    }
	}

	public void updateDisplay() {
        obsList.clear();
		for(Album a:albums) {
			obsList.add(a.getName());
		}
		listAlbum.setItems(obsList);
        if(albums.size() > 0)
            listAlbum.getSelectionModel().select(0);
	}

	private void alert(String title, String headerText, String contentText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private boolean checkForDuplicate(String name){
        List<Album> nameList = currentUser.getAlbums();
	    for(int i = 0; i < nameList.size(); i++){
	        if(nameList.get(i).getName().equals(name))
	            return true;
        }
	    return false;
    }
}
