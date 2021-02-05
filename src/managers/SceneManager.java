package managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class SceneManager {
    public static final String SIDEBAR = "../application/sidebar/sidebar.fxml";
    public static final String LOGIN_SCENE = "../application/login/login.fxml";

    public static final String APPOINTMENTS_SCENE = "../application/appointments/appointments.fxml";
    public static final String APPOINTMENT_SCHEDULE_SCENE = "../application/appointments/appointment-schedule.fxml";

    public static final String CUSTOMERS_SCENE = "../application/customers/customers.fxml";
    public static final String CUSTOMER_UPSERT_SCENE = "../application/customers/customers-upsert.fxml";

    private String previousScene;
    private String currentScene;

    private static SceneManager instance = null;
    private DataManager dataManager;
    private Stage window;
    private Parent sidebar;
    private BorderPane borderPane;

    private SceneManager() {
        dataManager = DataManager.getInstance();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void initialize(Stage primaryStage) {
        try {
            var currentLocale = Locale.getDefault();
            var loginResource = ResourceBundle.getBundle("i18n.Login", currentLocale);
            var fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_SCENE));

            fxmlLoader.setResources(loginResource);

            Parent root = fxmlLoader.load();
            this.setWindow(primaryStage);
            primaryStage.setTitle(loginResource.getString("title"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.print("An error occurred while attempting to initialize scenes" + System.lineSeparator() + e.getMessage());
        }
    }

    public void goToScene(String fxml) {
        goToScene(fxml, -1, -1);
    }

    public void goToScene(String fxml, int customerId) {
        goToScene(fxml, customerId, -1);
    }

    public void goToScene(String fxml, int customerId, int appointmentId) {
        try {
            dataManager.setCustomerId(customerId);
            dataManager.setAppointmentId(appointmentId);
            var cls = getClass();
            var resource = cls.getResource(fxml);
            Parent main = FXMLLoader.load(resource);

            borderPane.setCenter(main);
            window.centerOnScreen();

            previousScene = currentScene;
            currentScene = fxml;
        } catch (Exception e) {
            System.out.print("An error occurred while attempting to initialize scenes" + System.lineSeparator() + e.getMessage());
        }
    }

    public void setupBorderPane() {
        try {
            sidebar = FXMLLoader.load(getClass().getResource(SIDEBAR));

            borderPane = new BorderPane();
            borderPane.setLeft(sidebar);

            Scene scene = new Scene(borderPane);

            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println("An Error occurred when attempting to change scenes");
        }
    }

    public void setScreenSize() {
        window.setWidth(960);
        window.setHeight(540);
    }

    private void setWindow(Stage window) {
        this.window = window;
    }

    public void goToPreviousScene() {
        goToScene(previousScene);
    }
}
