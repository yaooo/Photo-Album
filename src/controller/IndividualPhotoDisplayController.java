package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UserList;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;


public class IndividualPhotoDisplayController {

    @FXML
    private ImageView PhotoDisplay;

    @FXML
    private ListView IndividualTagList;

    @FXML
    private Button IndividualDone;

    private User currentUsername;
    private Album album;
    public void start(Stage Stage, Photo photo, User username, Album a) throws ClassNotFoundException, IOException {
//        PhotoDisplay = new ImageView();
        album = a;
        currentUsername = username;
        PhotoDisplay.setImage(photo.getImage());
    }

    private void DisplayTags(){
        //TODO

    }


    @FXML
    protected void handleDoneButton(ActionEvent event) throws IOException,  ClassNotFoundException {
       //todo: NEED TO TEST IT
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PhotoList.fxml"));
            parent = (Parent) loader.load();

            PhotoListController ctrl = loader.getController();
            Scene scene = new Scene(parent);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ctrl.start(app_stage,currentUsername,album);

            app_stage.setScene(scene);
            app_stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
