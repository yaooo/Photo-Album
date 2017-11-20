package controller;
import model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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

public class SearchController {
	
	@FXML
	private Button add;
	@FXML
	private Button delete;
	@FXML
	private Button exit;
	@FXML
	private ListView cList;
	@FXML
	private TextField input;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	
	UserList u;
	private List<Album> albums;
	User currentUser;
	private List<Tag> tags;
	private ObservableList<String> obsList;
	public void start(Stage stage , User username) throws ClassNotFoundException, IOException {
		currentUser=username;
		albums=new ArrayList<Album>();
		albums=currentUser.getAlbums();
		tags=new ArrayList<Tag>();
		u=new UserList();
		u=UserList.read();
		obsList = FXCollections.observableArrayList();
		
	}
	
	@FXML public void handleAddButton(ActionEvent event) {
		String s = input.getText();
		if(s.equals("") || s==(null)) {
			return;
		}
		String parts[]=s.split(":");
		Tag t = new Tag(parts[0],parts[1]);
		tags.add(t);
		obsList.add(parts[0]+"="+parts[1]);
		cList.setItems(obsList);
		
		
	}
	@FXML public void handleDeleteButton(ActionEvent event) {
		String s =(String) cList.getSelectionModel().getSelectedItem();
		String parts[]= s.split("=");
		Tag delete=null;
		for(Tag t:tags) {
			if(t.getType().equals(parts[0]) && t.getValue().equals(parts[1])) {
				obsList.remove(s);
			}
		}
		tags.remove(delete);
		cList.setItems(obsList);
		
		
	}
	@FXML public void handleExitButton(ActionEvent event) throws ClassNotFoundException, IOException {
		Parent parent;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
		parent = (Parent) loader.load();
		        
		AlbumController ctrl = loader.getController();
		Scene scene = new Scene(parent);
					
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
	                
		ctrl.start(app_stage , currentUser);
	             
	    app_stage.setScene(scene);
	    app_stage.show();  
    }
	@FXML public void handleSearchButton(ActionEvent event) throws ClassNotFoundException,IOException{
		Album temp = new Album("temp");
		if((startDate.getValue()!=null && endDate.getValue()==null) || 
		   (startDate.getValue()==null && endDate.getValue()!=null) || 
		   (startDate.getValue()!=null && startDate.getValue().isAfter(endDate.getValue()))){
			error("Please enter valid date range");
		}
		if(obsList.isEmpty() && startDate.getValue()!=null && endDate.getValue()!=null) {
			for(Album a:albums) {
				ArrayList<Photo> photos = new ArrayList<Photo>();
				photos=(ArrayList<Photo>) a.getPhotos();
				for(Photo p:photos) {
					ArrayList<Tag> tags2 = new ArrayList<Tag>();
					tags2=(ArrayList<Tag>) p.getTags();
					if(p.isWithinDateRange(startDate.getValue(), endDate.getValue())) {
							boolean exists=false;
							Photo insert=p.carbonCopy();
							SerializableImage tempImage = new SerializableImage();
							tempImage.setImage(p.getImage());
							for (Photo p2 : temp.getPhotos()) {
								if (tempImage.equals(p2.getSerializableImage())) {
									exists=true;
								}
							}
							if(exists==false) {
								temp.addPhoto(insert);
							}
					}
				}
			}

		}
		
		else if(!(obsList.isEmpty()) && startDate.getValue()==null && endDate.getValue()==null) {
			boolean containsAllTags=true;
			boolean containsTag=false;
			for(Album a:albums) {
				ArrayList<Photo> photos = new ArrayList<Photo>();
				photos=(ArrayList<Photo>) a.getPhotos();
				for(Photo p:photos) {
					containsAllTags=true;
					containsTag=false;
					ArrayList<Tag> tags2 = new ArrayList<Tag>();
					tags2=(ArrayList<Tag>) p.getTags();
					for(Tag c:tags) {
						for(Tag t:tags2) {
							if(t.getType().equals(c.getType()) && t.getValue().equals(c.getValue())) {
								containsTag=true;
							}
						}
						containsAllTags=containsAllTags && containsTag;
						containsTag=false;
					}
					if(containsAllTags) {
						boolean exists=false;
						Photo insert=p.carbonCopy();
						SerializableImage tempImage = new SerializableImage();
				        tempImage.setImage(p.getImage());
				        for (Photo p2 : temp.getPhotos()) {
				            if (tempImage.equals(p2.getSerializableImage())) {
				                exists=true;
				            }
				        }
				        if(exists==false) {
				        	temp.addPhoto(insert);
				        }
					}
				}
			}
		}
		else {
			for(Album a:albums) {
				boolean containsAllTags=true;
				boolean containsTag=false;
				ArrayList<Photo> photos = new ArrayList<Photo>();
				photos=(ArrayList<Photo>) a.getPhotos();
				for(Photo p:photos) {
					if(p.isWithinDateRange(startDate.getValue(), endDate.getValue())) {
						containsAllTags=true;
						containsTag=false;
						ArrayList<Tag> tags2 = new ArrayList<Tag>();
						tags2=(ArrayList<Tag>) p.getTags();
						for(Tag c:tags) {
							for(Tag t:tags2) {
								if(t.getType().equals(c.getType()) && t.getValue().equals(c.getValue())) {
									containsTag=true;
								}
							}
							containsAllTags=containsAllTags && containsTag;
							containsTag=false;
						}
						if(containsAllTags){
							boolean exists=false;
							Photo insert=p.carbonCopy();
							SerializableImage tempImage = new SerializableImage();
					        tempImage.setImage(p.getImage());
					        for (Photo p2 : temp.getPhotos()) {
					            if (tempImage.equals(p2.getSerializableImage())) {
					                exists=true;
					            }
					        }
					        if(exists==false) {
					        	temp.addPhoto(insert);
					        }
						}
					}
				}
			}
			
		}
		Parent parent;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SearchResults.fxml"));
		parent = (Parent) loader.load();
		        
		SearchResultsController ctrl = loader.getController();
		Scene scene = new Scene(parent);
					
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
	                
		ctrl.start(app_stage,currentUser,temp);
	             
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
