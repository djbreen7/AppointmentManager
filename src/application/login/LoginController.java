package application.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void handleLoginBtnAction(ActionEvent event) {
        System.out.println("Logging in");
        System.out.println(userNameField.getText());
        System.out.println(passwordField.getText());
    }
}
