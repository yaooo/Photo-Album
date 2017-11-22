package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

import java.io.IOException;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */

public class IndividualPhotoDisplayController {

    @FXML
    private ImageView PhotoDisplay;

    @FXML
    private ListView IndividualTagList;

    @FXML
    private TextArea IndividualCaption;

    @FXML
    private Text DateOfPhoto;

    private User currentUsername;
    private Album album;
    private Photo currentPhoto;
    private ObservableList<String> obsList;

    /**
     * Start
     *
     * @param Stage    stage
     * @param photo    photo
     * @param username name
     * @param a        album
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void start(Stage Stage, Photo photo, User username, Album a) throws ClassNotFoundException, IOException {
//        PhotoDisplay = new ImageView();
        album = a;
        currentUsername = username;
        currentPhoto = photo;
        PhotoDisplay.setImage(photo.getImage());
        obsList = FXCollections.observableArrayList();
        DisplayTags();
        DisplayCaption();
        DisplayDateTime();
    }

    /**
     * Display tags
     */
    private void DisplayTags() {
        obsList.clear();
        for (Tag t : currentPhoto.getTags()) {
            obsList.add(t.toString());
        }
        IndividualTagList.setItems(obsList);
    }


    /**
     * Display caption
     */
    private void DisplayCaption() {
        String c = currentPhoto.getCaption();
        IndividualCaption.setEditable(false);

        IndividualCaption.setWrapText(true);     // New line of the text exceeds the text area
        IndividualCaption.setPrefRowCount(10);
        IndividualCaption.setText(c);
    }

    /**
     * Display date and time
     */
    private void DisplayDateTime() {
        String time = currentPhoto.getDate();
        DateOfPhoto.setText(time);
    }

    /**
     * Handle the done button
     */
    @FXML
    protected void handleDoneButton(ActionEvent event) throws IOException, ClassNotFoundException {
        //todo: NEED TO TEST IT
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
