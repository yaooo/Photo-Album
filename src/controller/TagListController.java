package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import model.*;
import controller.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagListController {
    @FXML
    Text phototNameTag;
    @FXML
    private Button addTag;
    @FXML
    private Button deleteTag;
    @FXML
    private Button editTag;
    @FXML
    private Button doneTag;
    @FXML
    ListView listTag;

    private ObservableList<String> obsList;
    private List<Tag> tags;
    private Photo currentPhoto;
    UserList u;
    User currentUser;
    Album currentAlbum;

    public void start(Stage Stage, User username, Album a, Photo p) throws ClassNotFoundException, IOException {
        u = new UserList();
        u = UserList.read();
        currentUser = username;
        currentPhoto = p;
        currentAlbum = a;
        tags = currentPhoto.getTags();
        obsList = FXCollections.observableArrayList();
        display(tags);
    }

    private void display(List<Tag> tags) {
        if (obsList != null)
            obsList.clear();

        if (tags == null)
            return;

        if (tags.size() == 0)
            return;

        for (Tag p : tags) {
            String temp = p.getType() + " : " + p.getValue();
            obsList.add(temp);
        }
        listTag.setItems(obsList);
    }

    @FXML
    protected void handleDeleteButton(ActionEvent event) throws IOException, ClassNotFoundException {
        if(tags.size() < 1)
            return;

        int s = listTag.getSelectionModel().getSelectedIndex();
        currentPhoto.removeTag(s);
        tags = currentPhoto.getTags();
        display(tags);

    }

    @FXML
    protected void handleAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New tag");
        dialog.setHeaderText("Please enter your tag, use the format \"type:value\"");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (result.get().indexOf(':') == -1 || result.get().indexOf(':') == 0) {
                alert("Error", "Input invalid.", "Please follow the correct format");
                return;
            }

            String parts[] = result.get().split(":");
            currentPhoto.addTag(parts[0], parts[1]);
            tags = currentPhoto.getTags();
            display(tags);
        }
    }

    @FXML
    protected void handleEditButton(ActionEvent event) throws IOException, ClassNotFoundException {

        if (tags.size() < 1) {
            return;
        }

        int s = listTag.getSelectionModel().getSelectedIndex();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New tag");
        dialog.setHeaderText("Please enter your tag, use the format \"type:value\"");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().indexOf(':') == -1 || result.get().indexOf(':') == 0) {
                alert("Error", "Input invalid.", "Please follow the correct format");
                return;
            }
            String parts[] = result.get().split(":");
            currentPhoto.editTag(s, parts[0], parts[1]);
            tags = currentPhoto.getTags();
            display(tags);
        }

    }

    @FXML
    protected void handleExitButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent parent;
        try {
            u.removeUser(currentUser.getName());
            u.addUser(currentUser);
            u.write(u);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PhotoList.fxml"));
            parent = (Parent) loader.load();

            PhotoListController ctrl = loader.getController();
            Scene scene = new Scene(parent);

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            ctrl.start(app_stage, currentUser, currentAlbum);

            app_stage.setScene(scene);
            app_stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void alert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
