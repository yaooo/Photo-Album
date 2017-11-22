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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserList;

import java.io.IOException;
/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class AdminListController {
//	@FXML
//	private Button Create;
//	@FXML
//	private Button Delete;
//	@FXML
//	private Button Exit;
	@FXML
	private ListView List;
	@FXML
	private TextField inputField;
	
	private UserList u;
	private ObservableList<String> obsList = FXCollections.observableArrayList();    
	public void start(Stage stage) throws IOException,ClassNotFoundException {
		u=new UserList();
		u=UserList.read();
		updateDisplay();
	      // select the first item
	    List.getSelectionModel().select(0);

	     
	}
	@FXML protected void handleExitButton(ActionEvent event) throws ClassNotFoundException{
		Parent parent;
		
		 try {
			 		u.write(u);
			 		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
					parent = (Parent) loader.load();
					        
					LoginController ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
		        
		 }catch (IOException e) {
		    	e.printStackTrace();
		    }
	}

	@FXML protected void handleCreateButton(ActionEvent event) throws ClassNotFoundException{
		String newUser=inputField.getText();
		
		if(newUser==null || newUser.equals("")) {
			error("Please enter a valid username!");
		}
		else if (obsList.contains(newUser)) {
			error("User already exists!");
		}
		else {
			User insert= new User(newUser);
			u.addUser(insert);
			updateDisplay();
            inputField.clear();
		}

	}
	
	@FXML protected void handleDeleteButton(ActionEvent event) throws ClassNotFoundException{
		String s =(String) List.getSelectionModel().getSelectedItem();
		u.removeUser(s);
		updateDisplay();
	}
	
	public void updateDisplay() {
		obsList.clear();
		for(User a : u.getUserList()) {
			obsList.add(a.getName());
		}
		List.setItems(obsList);
	}
	private void error(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Input error");
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
