package application.login;

import implementations.UserDaoImpl;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import managers.SceneManager;
import managers.UserManager;

public class LoginController {
    private UserDao userDao;
    private SceneManager sceneManager;

    public void initialize() {
        userDao = new UserDaoImpl();
        sceneManager = SceneManager.getInstance();
    }

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
            sceneManager.setScreenSize();
            sceneManager.setupBorderPane();
            sceneManager.goToScene(sceneManager.APPOINTMENTS_SCENE);
        }
    }
}
