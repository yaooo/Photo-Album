package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlbumController {
	@FXML
	 private ListView listAlbum;
	@FXML
	private Button DeleteAlbum;
	@FXML
	private Button RenameAlbum;
	@FXML
	private Button OpenAlbum;
	@FXML
	private Button Exit;
	@FXML
	private Button CreateAlbum;
	
	public void start() throws IOException {
		
	}
}
