package application.login;

import application.BaseSceneController;
import implementations.UserDaoImpl;
import interfaces.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import managers.UserManager;

public class LoginController extends BaseSceneController {
    private UserDao userDao = new UserDaoImpl();

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void handleLoginBtnAction(ActionEvent event) {
        var userManager = UserManager.getInstance();
        var userName = userNameField.getText();
        var password = passwordField.getText();
        var user = userDao.getUser(userName);
        var isSuccess = password.equals(user.getPassword());

        if (isSuccess) {
            userManager.setCurrentUser(user);
            goToScene(MAIN_PATH);
        }
    }
}
