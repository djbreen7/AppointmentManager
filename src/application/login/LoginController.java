package application.login;

import dao.UserDao;
import implementations.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logger.ActivityLogger;
import managers.SceneManager;
import managers.UserManager;

public class LoginController {
    private UserDao userDao;
    private SceneManager sceneManager;
    private ActivityLogger logger;

    public void initialize() {
        userDao = new UserDaoImpl();
        logger = new ActivityLogger();
        sceneManager = SceneManager.getInstance();
    }

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLoginBtnAction(ActionEvent event) {
        var userManager = UserManager.getInstance();
        var userName = userNameField.getText();
        var password = passwordField.getText();
        var user = userDao.getUser(userName);
        var isSuccess = user != null && password.equals(user.getPassword());
        var message = isSuccess ? "logged in" : "failed login attempt";
        logger.info(userName, message);

        if (isSuccess) {
            userManager.setCurrentUser(user);
            sceneManager.setScreenSize();
            sceneManager.setupBorderPane();
            sceneManager.goToScene(sceneManager.APPOINTMENTS_SCENE);
            return;
        }

        errorLabel.setVisible(true);
    }
}
