package controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import controller.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.SerializableImage;
import model.User;
import model.UserList;
public class PhotoListController {
    @FXML
    private Text albumTitle1;
    @FXML
    private Button copyPhoto1;
    @FXML
    private Button movePhoto1;
    @FXML
    private Button addPhoto1, removePhoto1;
    @FXML
    private Button renamePhoto1;
    @FXML
    private Button photoTag1;
    @FXML
    private Button slideShow1;
    @FXML
    private Button exit1;
    @FXML
    private ListView photoList1;

    private ObservableList<ImageView> storeImg;

    private ObservableList<Photo> obsList;
	private List<Photo> photos;
	private Album album;
	private User currentUser;
	private UserList u;

	public void start(Stage Stage, User username,Album a) throws ClassNotFoundException, IOException {
		u=new UserList();
		u=UserList.read();
		currentUser=username;
		photos = new ArrayList<Photo>();
		album = a;
		photos = album.getPhotos();
		storeImg = FXCollections.observableArrayList();
//		System.out.println(album.getCount());
		obsList = FXCollections.observableArrayList(photos);
		display(photos);
        albumTitle1.setText(album.getName());
    }

	private void display(List<Photo> photoList){
	    if(storeImg != null)
    	    storeImg.clear();

	    if(photoList == null)
	        return;

	    if(photoList.size() == 0)
	        return;

	    for(Photo p : photoList){
            Image img = p.getImage();
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setPreserveRatio(true);

            imgView.setFitWidth(100);

            storeImg.add(imgView);
        }
        photoList1.setItems(storeImg);
    }



	@FXML protected void handleAddButton(ActionEvent event) throws IOException {
		choosePhoto(event);
	}
	@FXML
	protected void choosePhoto(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		
		fileChooser.setTitle("Upload Photo");
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(app_stage);
		
		if (file == null)
			return;
		
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        
        //check to see if this photo exists in the album
        SerializableImage tempImage = new SerializableImage();
        tempImage.setImage(image);
        for (Photo p: album.getPhotos()) {
        	if (tempImage.equals(p.getSerializableImage())) {
        		return;
        	}
        }
        Photo tempPhoto = null;
        boolean photoFound = false;
        
        //check to see if this photo exists in other albums
        //then tempPhoto = the photo object that it equals to
        for (Album a: currentUser.getAlbums()) {
        	for (Photo p: a.getPhotos()) {
        		if (tempImage.equals(p.getSerializableImage())) {
        			tempPhoto = p;
        			photoFound = true;
        			System.out.println("Found the photo!");
        			break;
        		}
        		if (photoFound)
        			break;
        	}
        }
        
        //else, create a new photo object
        if (!photoFound)
        	tempPhoto = new Photo(image);

        album.addPhoto(tempPhoto);
        obsList.add(tempPhoto);
		UserList.write(u);
        display(photos);

	}
	@FXML protected void handleExitButton(ActionEvent event)throws IOException, ClassNotFoundException{
		Parent parent;
		 try {
			 		u.removeUser(currentUser.getName());
					u.addUser(currentUser);
			 		u.write(u);
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
					parent = (Parent) loader.load();
					        
					AlbumController ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage,currentUser);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
		        
		 }catch (IOException e) {
		    	e.printStackTrace();
		 }
	}

    @FXML
    protected void handleRemoveButton(ActionEvent event)throws IOException, ClassNotFoundException{
        if(photos == null)
            return;
        if(photos.size() == 0)
            return;
	    int s = photoList1.getSelectionModel().getSelectedIndex();
        if(s >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                photos.remove(s);
            } else {
                return;
            }
        }
        display(photos);
    }

    @FXML
    protected void handleCopyToButton(ActionEvent event)throws IOException, ClassNotFoundException{
        if(photos == null)
            return;
        if(photos.size() == 0)
            return;
        int s = photoList1.getSelectionModel().getSelectedIndex();
        List <Album> abs = currentUser.getAlbums();

        if(abs.size() == 1){
            alert("Error.", "Cannot copy to other album(s).", "There is no other album.");
        }

        List<String> choices = new ArrayList<>();

        String defaulSelection = null;
        for(Album temp : abs){
            if(!temp.getName().equals(this.album.getName())){
                choices.add(temp.getName());
                defaulSelection = temp.getName();
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaulSelection, choices);
        dialog.setTitle("Copy To");
        dialog.setHeaderText("Choose an album");
        dialog.setContentText("Copy to:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String dest_album_name = result.get();
            Album dest_album = currentUser.getAlbumByName(dest_album_name);

            dest_album.addPhoto(photos.get(s));

        }
	}

	@FXML
	protected void handleMoveButton(ActionEvent event)throws IOException, ClassNotFoundException{
        if(photos == null)
            return;
        if(photos.size() == 0)
            return;
        int s = photoList1.getSelectionModel().getSelectedIndex();
        List <Album> abs = currentUser.getAlbums();

        if(abs.size() == 1){
            alert("Error.", "Cannot move the selection to other album(s).", "There is no other album.");
        }

        List<String> choices = new ArrayList<>();

        String defaulSelection = null;
        for(Album temp : abs){
            if(!temp.getName().equals(this.album.getName())){
                choices.add(temp.getName());
                defaulSelection = temp.getName();
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaulSelection, choices);
        dialog.setTitle("Move To");
        dialog.setHeaderText("Choose an album");
        dialog.setContentText("Move to:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String dest_album_name = result.get();
            Album dest_album = currentUser.getAlbumByName(dest_album_name);
            dest_album.addPhoto(photos.get(s));
            photos.remove(s);
        }
    }

    @FXML
    protected void handleSlideShowButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }

    @FXML
    protected void handleRenameButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }

    @FXML
    protected void handleTagButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }

    private void alert(String title, String headerText, String contentText){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
