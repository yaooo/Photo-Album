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
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class TagListController {

    @FXML
    ListView listTag;

    private ObservableList<String> obsList;
    private List<Tag> tags;
    private Photo currentPhoto;
    private UserList u;
    private User currentUser;
    private Album currentAlbum;

    public void start(User username, Album a, Photo p) throws ClassNotFoundException, IOException {
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

        if(isSelected(s))
            return;

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
                alert("Error", "Input invalid.", "Please follow the correct format.");
                return;
            }

            String parts[] = result.get().split(":");
            if(duplicatedTag(parts[0], parts[1])){
                alert("Error", "Duplicated tag error.", "Please try a different tag.");
                return;
            }
            currentPhoto.addTag(parts[0], parts[1]);
            tags = currentPhoto.getTags();
            display(tags);
        }
    }

    @FXML
    protected void handleEditButton(ActionEvent event) throws IOException, ClassNotFoundException {
        if (tags.size() < 1)
            return;

        int s = listTag.getSelectionModel().getSelectedIndex();

        if(isSelected(s))
            return;

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
            if(duplicatedTag(parts[0], parts[1])){
                alert("Error", "Duplicated tag error.", "Please try again.");
                return;
            }
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

    private boolean isSelected(int i){
        if(i == -1) {
            alert("Error", "Selection invalid.", "Please select an item in the list.");
            return true;
        }
        return false;
    }

    private boolean duplicatedTag(String type, String value){
        for(Tag t : tags){
            String name_test = t.getType();
            String value_test = t.getValue();
            if(name_test.equals(type) && value_test.equals(value)){
                return true;
            }
        }
        return false;
    }

}
