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

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */

public class SlideShowController {

    @FXML
    private ImageView displaySlideShow;

    private User currentUsername;
    private Album album;
    private List<Photo> photos;
    private int index;
    private int size_photos;

    /**
     * Start
     *
     * @param Stage    stage
     * @param username name
     * @param a        album
     * @throws ClassNotFoundException not found
     * @throws IOException            exception
     */
    public void start(Stage Stage, User username, Album a) throws ClassNotFoundException, IOException {
        album = a;
        currentUsername = username;
        photos = album.getPhotos();
        index = 0;
        displaySlideShow.setImage(photos.get(0).getImage());
        size_photos = photos.size();
    }


    /**
     * Handle the next button
     */
    @FXML
    protected void handleNextButton(ActionEvent event) throws IOException {
        if (size_photos > 1) {
            index++;
            if (index == size_photos)
                index = 0;
        }
        displaySlideShow.setImage(photos.get(index).getImage());

    }

    /**
     * Handle the previous button
     */
    @FXML
    protected void handlePreviousButton(ActionEvent event) throws IOException {
        if (size_photos > 1) {
            index--;
            if (index == -1)
                index = size_photos - 1;
        }
        displaySlideShow.setImage(photos.get(index).getImage());
    }


    /**
     * Handle the finish button
     */
    @FXML
    protected void handleFinishButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PhotoList.fxml"));
            parent = (Parent) loader.load();

            PhotoListController ctrl = loader.getController();
            Scene scene = new Scene(parent);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ctrl.start(app_stage, currentUsername, album);

            app_stage.setScene(scene);
            app_stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
