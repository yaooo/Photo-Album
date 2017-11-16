package controller;
import java.io.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.application.*;
public class AdminListController {
	@FXML
	private Button Create;
	@FXML
	private Button Delete;
	@FXML
	private Button Exit;
	@FXML
	private ListView UserList;
	@FXML
	private TextField inputField;
	
	private ObservableList<String> obsList = FXCollections.observableArrayList();    
	public void start(Stage stage) throws IOException {
		File Users = new File("UserList.txt");
		FileReader UserFR = new FileReader(Users);
		BufferedReader UserBR=new BufferedReader(UserFR);
		String username;
		username=UserBR.readLine();
		while(username!=null) {
			obsList.add(username);
			username=UserBR.readLine();
		}
		
		UserList.setItems(obsList);
	      // select the first item
	    UserList.getSelectionModel().select(0);

	     
	}
	@FXML protected void handleExitButton(ActionEvent event) throws ClassNotFoundException{
		Parent parent;
		
		 try {
			 		File Users = new File("UserList.txt");
			 		FileWriter UserFW=new FileWriter(Users);
			 		BufferedWriter UserBW=new BufferedWriter(UserFW);
		        	for(int i=0;i<obsList.size();i++) {
		        		UserBW.write(obsList.get(i));
		        		if(i!=obsList.size()-1) {
		        			UserBW.newLine();
		        		}
		        	}
		        	UserBW.flush();
		        	UserBW.close();
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
			System.out.println("please enter a valid username!");
		}
		else if (obsList.contains(newUser)) {
			System.out.println("user already exsists!");
		}
		else {
			obsList.add(newUser);
		}
	}
	
	@FXML protected void handleDeleteButton(ActionEvent event) throws ClassNotFoundException{
		String s =(String) UserList.getSelectionModel().getSelectedItem();
		obsList.remove(s);
	}
}
