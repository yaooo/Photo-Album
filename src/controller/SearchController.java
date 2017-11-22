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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class SearchController {

    @FXML
    private ListView cList;
    @FXML
    private TextField input;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    private UserList u;
    private List<Album> albums;
    private User currentUser;
    private List<Tag> tags;
    private ObservableList<String> obsList;

    /**
     * Start
     *
     * @param stage    stage
     * @param username name
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void start(Stage stage, User username) throws ClassNotFoundException, IOException {
        currentUser = username;
        albums = new ArrayList<Album>();
        albums = currentUser.getAlbums();
        tags = new ArrayList<Tag>();
        u = new UserList();
        u = UserList.read();
        obsList = FXCollections.observableArrayList();

    }

    /**
     * Handle the add button
     */
    @FXML
    public void handleAddButton(ActionEvent event) {
        String s = input.getText();
        if (s == null) {
            alert("Error", "Input Format Error.", "The input format should be \"Type:Value\". \nFor example, \"Person,stock\".");
            return;
        }
        if (s.equals("")) {
            alert("Error", "Input Format Error.", "The input format should be \"Type:Value\". \nFor example, \"Person,stock\".");
            return;
        }

        if (!s.contains(":")){
            alert("Error", "Input Format Error.", "The input format should be \"Type:Value\". \nFor example, \"Person,stock\".");
            return;
        }

        String temp = s;
        int count = s.length() - temp.replace(":", "").length();

        if (count != 1){
            alert("Error", "Input Format Error.", "The input format should be \"Type:Value\". \nFor example, \"Person,stock\".");
            return;
        }


        String parts[] = s.split(":");
        if(parts.length != 2){
            alert("Error", "Input Format Error.", "The input format should be \"Type:Value\". \nFor example, \"Person,stock\".");
            return;
        }


        Tag t = new Tag(parts[0], parts[1]);
        tags.add(t);
        obsList.add(parts[0] + "=" + parts[1]);
        cList.setItems(obsList);


    }

    /**
     * Handle the delete button
     */
    @FXML
    public void handleDeleteButton(ActionEvent event) {
        if (cList.getItems().size() == 0) {
            alert("Error", "Invalid selection.", "The list is already empty.");
            return;
        }

        String s = (String) cList.getSelectionModel().getSelectedItem();

        if (cList.getSelectionModel().getSelectedIndex() == -1) {
            alert("Error", "Invalid selection.", "Please select an item to delete.");
            return;
        }

        String parts[] = s.split("=");
        Tag delete = null;
        for (Tag t : tags) {
            if (t.getType().equals(parts[0]) && t.getValue().equals(parts[1])) {
                obsList.remove(s);
            }
        }
        tags.remove(delete);
        cList.setItems(obsList);


    }

    /**
     * Handle the exit button
     */
    @FXML
    public void handleExitButton(ActionEvent event) throws ClassNotFoundException, IOException {
        Parent parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
        parent = (Parent) loader.load();

        AlbumController ctrl = loader.getController();
        Scene scene = new Scene(parent);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ctrl.start(app_stage, currentUser);

        app_stage.setScene(scene);
        app_stage.show();
    }

    /**
     * Handle the search button
     */
    @FXML
    public void handleSearchButton(ActionEvent event) throws ClassNotFoundException, IOException {
        Album temp = new Album("temp");
        if (cList.getItems().size() == 0 && (startDate.getValue() == null || endDate.getValue() == null)) {
            alert("Error", "Miss search criteria.", "Please add the search criteria first.");
            return;
        }

        if ((startDate.getValue() != null && endDate.getValue() == null) ||
                (startDate.getValue() == null && endDate.getValue() != null) ||
                (startDate.getValue() != null && startDate.getValue().isAfter(endDate.getValue()))) {
            error("Please enter valid date range");
        }
        if (obsList.isEmpty() && startDate.getValue() != null && endDate.getValue() != null) {
            for (Album a : albums) {
                ArrayList<Photo> photos = new ArrayList<Photo>();
                photos = (ArrayList<Photo>) a.getPhotos();
                for (Photo p : photos) {
                    ArrayList<Tag> tags2 = new ArrayList<Tag>();
                    tags2 = (ArrayList<Tag>) p.getTags();
                    if (p.isWithinDateRange(startDate.getValue(), endDate.getValue())) {
                        boolean exists = false;
                        SerializableImage tempImage = new SerializableImage();
                        tempImage.setImage(p.getImage());
                        for (Photo p2 : temp.getPhotos()) {
                            if (tempImage.equals(p2.getSerializableImage())) {
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            temp.addPhoto(p);
                        }
                    }
                }
            }

        } else if (!(obsList.isEmpty()) && startDate.getValue() == null && endDate.getValue() == null) {
            boolean containsAllTags = true;
            boolean containsTag = false;
            for (Album a : albums) {
                ArrayList<Photo> photos = new ArrayList<Photo>();
                photos = (ArrayList<Photo>) a.getPhotos();
                for (Photo p : photos) {
                    containsAllTags = true;
                    containsTag = false;
                    ArrayList<Tag> tags2 = new ArrayList<Tag>();
                    tags2 = (ArrayList<Tag>) p.getTags();
                    for (Tag c : tags) {
                        for (Tag t : tags2) {
                            if (t.getType().equals(c.getType()) && t.getValue().equals(c.getValue())) {
                                containsTag = true;
                            }
                        }
                        containsAllTags = containsAllTags && containsTag;
                        containsTag = false;
                    }
                    if (containsAllTags) {
                        boolean exists = false;
                        SerializableImage tempImage = new SerializableImage();
                        tempImage.setImage(p.getImage());
                        for (Photo p2 : temp.getPhotos()) {
                            if (tempImage.equals(p2.getSerializableImage())) {
                                exists = true;
                            }
                        }
                        if (!exists) {
                            temp.addPhoto(p);
                        }
                    }
                }
            }
        } else {
            for (Album a : albums) {
                boolean containsAllTags = true;
                boolean containsTag = false;
                ArrayList<Photo> photos = new ArrayList<Photo>();
                photos = (ArrayList<Photo>) a.getPhotos();
                for (Photo p : photos) {
                    if (p.isWithinDateRange(startDate.getValue(), endDate.getValue())) {
                        containsAllTags = true;
                        containsTag = false;
                        ArrayList<Tag> tags2 = new ArrayList<Tag>();
                        tags2 = (ArrayList<Tag>) p.getTags();
                        for (Tag c : tags) {
                            for (Tag t : tags2) {
                                if (t.getType().equals(c.getType()) && t.getValue().equals(c.getValue())) {
                                    containsTag = true;
                                }
                            }
                            containsAllTags = containsAllTags && containsTag;
                            containsTag = false;
                        }
                        if (containsAllTags) {
                            boolean exists = false;
                            SerializableImage tempImage = new SerializableImage();
                            tempImage.setImage(p.getImage());
                            for (Photo p2 : temp.getPhotos()) {
                                if (tempImage.equals(p2.getSerializableImage())) {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                temp.addPhoto(p);
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

        ctrl.start(app_stage, currentUser, temp);

        app_stage.setScene(scene);
        app_stage.show();


    }

    /**
     * Error
     *
     * @param msg message
     */
    private void error(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Input error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Alert
     *
     * @param title       title
     * @param headerText  header
     * @param contentText content
     */
    private void alert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
