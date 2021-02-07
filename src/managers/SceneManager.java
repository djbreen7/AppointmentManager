package managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A Singleton class for managing scene routing and navigation.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class SceneManager {
    public static final String SIDEBAR = "../application/sidebar/sidebar.fxml";
    public static final String LOGIN_SCENE = "../application/login/login.fxml";

    public static final String APPOINTMENTS_SCENE = "../application/appointments/appointments.fxml";
    public static final String APPOINTMENT_SCHEDULE_SCENE = "../application/appointments/appointment-schedule.fxml";

    public static final String CUSTOMERS_SCENE = "../application/customers/customers.fxml";
    public static final String CUSTOMER_UPSERT_SCENE = "../application/customers/customers-upsert.fxml";

    public static final String REPORTING_SCENE = "../application/reporting/reporting.fxml";

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

    
    /**
     * Creates a SceneManager instance if once doesn't exist, then returns the instance.
     *
     * @return SceneManager
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    
    /**
     * Initializes the first scene;
     *
     * @param primaryStage
     */
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

    
    /**
     * Navigates to a scene without any data being shared.
     *
     * @param fxml The path to the scene file.
     */
    public void goToScene(String fxml) {
        goToScene(fxml, 0, 0);
    }

    
    /**
     * Navigates to a scene with customer data.
     *
     * @param fxml The path to the scene file.
     * @param customerId The id of the customer being modified or scheduled.
     */
    public void goToScene(String fxml, int customerId) {
        goToScene(fxml, customerId, 0);
    }

    
    /**
     * Navigates to a scene with customer and appointment data.
     *
     * @param fxml The path to the scene file.
     * @param customerId The id of the customer being modified or scheduled.
     * @param appointmentId THe id of the appointment being modified or scheduled.
     */
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

    /**
     * Adds the sidebar to the application.
     */
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

    /**
     * Configures the window size.
     */
    public void setScreenSize() {
        window.setWidth(960);
        window.setHeight(540);
    }

    private void setWindow(Stage window) {
        this.window = window;
    }

    /**
     * Sends the user to the previous scene.
     */
    public void goToPreviousScene() {
        goToScene(previousScene);
    }
}
