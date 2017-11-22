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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class SearchResultsController {
    @FXML
    private ListView photoList1;
    @FXML 
    private ListView captionList;
    
    private ObservableList<ImageView> storeImg;
    private ObservableList<Photo> obsList;
    private List<Photo> photos;
    private ObservableList<String> obsList2;
    Album temp ;
    UserList u;
    User currentUser;
    public void start(Stage Stage, User username,Album results) throws ClassNotFoundException, IOException {
    	currentUser=username;
    	temp=results;
    	u=new UserList();
    	u=UserList.read();
    	photos = new ArrayList<Photo>();
    	photos=results.getPhotos();
    	storeImg = FXCollections.observableArrayList();
    	obsList = FXCollections.observableArrayList(photos);
    	obsList2 = FXCollections.observableArrayList();
    	display(photos);
    	captionList.setMouseTransparent( true );
        captionList.setFocusTraversable( false );
    }
    private void display(List<Photo> photoList) {
    	obsList2.clear();
        if (storeImg != null)
            storeImg.clear();

        if (photoList == null)
            return;

        if (photoList.size() == 0)
            return;

        for (Photo p : photoList) {
            Image img = p.getImage();
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setPreserveRatio(true);

            imgView.setFitWidth(100);

            storeImg.add(imgView);
            String h="";
            for(Tag t : p.getTags()) {
            	h+=t.getValue()+" ,";
            }
            if(h.equals("")) {
            	obsList2.add("");
            }
            else{
            	h=h.substring(0, h.length()-1);
            	obsList2.add(p.getCaption()+" ,"+h);
            }
        }
        photoList1.setItems(storeImg);
        captionList.setItems(obsList2);
    }

    @FXML protected void handleExitButton(ActionEvent event) throws ClassNotFoundException,IOException {
    	Parent parent;
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
    	parent = (Parent) loader.load();
    	AlbumController ctrl = loader.getController();
		Scene scene = new Scene(parent);				
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	                
		ctrl.start(app_stage , u.getUserByUsername(currentUser.getName()));
		app_stage.setScene(scene);
		app_stage.show();  
        
    }
    @FXML protected void handleCreateButton(ActionEvent event) throws ClassNotFoundException,IOException {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Album Title");
        dialog.setHeaderText("Name:");

        Optional<String> result = dialog.showAndWait();
        if(result.get().equals("")|| result.get()==null) {
        	error("Please enter a valid name");
        	return;
        }
        temp.setName(result.get());
    	currentUser.addAlbum(temp);
    	u.removeUser(currentUser.getName());
    	u.addUser(currentUser);
    	Parent parent;
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
    	parent = (Parent) loader.load();
    	AlbumController ctrl = loader.getController();
		Scene scene = new Scene(parent);				
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	                
		ctrl.start(app_stage , u.getUserByUsername(currentUser.getName()));
		app_stage.setScene(scene);
		app_stage.show();  
    	
    	
    }
    private void error(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Input error");
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
