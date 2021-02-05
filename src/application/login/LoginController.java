package application.login;

import dao.UserDao;
import implementations.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logger.ActivityLogger;
import managers.SceneManager;
import managers.UserManager;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class LoginController implements Initializable {
    private UserDao userDao;
    private SceneManager sceneManager;
    private ActivityLogger logger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDao = new UserDaoImpl();
        logger = new ActivityLogger();
        sceneManager = SceneManager.getInstance();

        setTimezone();
    }

    private void setTimezone() {
        var zdt = ZonedDateTime.now();
        var timeZone = TimeZone.getTimeZone(zdt.getZone());
        var sb = new StringBuilder(zdt.getZone().toString());
        sb.append(" (").append(timeZone.getDisplayName()).append(")");

        timeZoneLabel.setText(sb.toString());
    }

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label timeZoneLabel;

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
