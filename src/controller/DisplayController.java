package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UserList;

import javax.swing.text.html.ListView;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;


public class DisplayController {

    @FXML
    private ImageView img;

    @FXML
    private ListView IndividualTagList;

    @FXML
    private Button Done;

    private User currentUsername;
    private Album album;
    public void start(Stage Stage, Photo photo, User username, Album a) throws ClassNotFoundException, IOException {
        album = a;
        currentUsername = username;
        img.setImage(photo.getImage());
    }

    private void DisplayTags(){
        //TODO

    }



    private void handleDoneButton(ActionEvent event) throws ClassNotFoundException, IOException{
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
