package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserList;

import java.io.IOException;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserList u;

    /**
     * Start
     *
     * @param stage stage
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void start(Stage stage) throws IOException, ClassNotFoundException {

    }

    /**
     * Initialize
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void initialize() throws IOException, ClassNotFoundException {
        u = new UserList();
        u = UserList.read();
    }

    /**
     * Handle the login button
     */
    @FXML
    protected void handleLoginButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Parent parent;

        try {
            if (username.equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminList.fxml"));
                parent = (Parent) loader.load();

                AdminListController ctrl = loader.getController();
                Scene scene = new Scene(parent);

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                ctrl.start(app_stage);

                app_stage.setScene(scene);
                app_stage.show();
            } else if (u.inList(username)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumList.fxml"));
                parent = (Parent) loader.load();

                AlbumController ctrl = loader.getController();
                Scene scene = new Scene(parent);

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                ctrl.start(app_stage, u.getUserByUsername(username));

                app_stage.setScene(scene);
                app_stage.show();
            } else {
                error("invalid Login!");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Error
     */
    private void error(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Input error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
