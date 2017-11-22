package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

import java.io.IOException;
import java.util.List;


public class SlideShowController {


//    @FXML
//    private Button finish, previous, next;
    @FXML
    private ImageView displaySlideShow;

    private User currentUsername;
    private Album album;
    private List<Photo> photos;
    private int index;
    private int size_photos;

    public void start(Stage Stage, User username, Album a) throws ClassNotFoundException, IOException {
//        PhotoDisplay = new ImageView();
        album = a;
        currentUsername = username;
        photos = album.getPhotos();
        index = 0;
        displaySlideShow.setImage(photos.get(0).getImage());
        size_photos = photos.size();
    }


    @FXML
    protected void handleNextButton(ActionEvent event) throws IOException{
        if(size_photos > 1){
            index ++;
            if(index == size_photos)
                index = 0;
        }
        displaySlideShow.setImage(photos.get(index).getImage());

    }

    @FXML
    protected void handlePreviousButton(ActionEvent event) throws IOException{
        if(size_photos > 1){
            index --;
            if(index == -1)
                index = size_photos - 1;
        }
        displaySlideShow.setImage(photos.get(index).getImage());
    }

    @FXML
    protected void handleFinishButton(ActionEvent event) throws IOException,  ClassNotFoundException {
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
