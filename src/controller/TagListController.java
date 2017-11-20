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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagListController {
    @FXML
    Text phototNameTag;

    @FXML
    Button addTag, deleteTag, editTag, doneTag;

    @FXML
    ListView listTag;

    private ObservableList<String> obsList;
    private List<Tag> tags;

    public void start(Stage Stage, User username, Album a) throws ClassNotFoundException, IOException {

        obsList = FXCollections.observableArrayList();
        display(tags);
    }

    private void display(List<Tag> tags){
        if(obsList != null)
            obsList.clear();

        if(tags == null)
            return;

        if(tags.size() == 0)
            return;

        for(Tag p : tags){
            String temp = "(" + p.getType() + "," + p.getValue() + ")";
            obsList.add(temp);
        }
        listTag.setItems(obsList);
    }

    @FXML
    protected void handleRemoveButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }

    @FXML
    protected void handleAddButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }

    @FXML
    protected void handleEditButton(ActionEvent event)throws IOException, ClassNotFoundException{


    }

    @FXML
    protected void handleExitButton(ActionEvent event)throws IOException, ClassNotFoundException{

    }
}
