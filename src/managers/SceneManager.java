package managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneManager {
    public static final String SIDEBAR = "../application/sidebar/sidebar.fxml";
    public static final String LOGIN_SCENE = "../application/login/login.fxml";
    public static final String APPOINTMENTS_SCENE = "../application/appointments/appointments.fxml";

    public static final String CUSTOMERS_SCENE = "../application/customers/customers.fxml";
    public static final String CUSTOMER_UPSERT_SCENE = "";

    private static SceneManager instance = null;
    private Stage window;
    private Parent sidebar;
    private BorderPane borderPane;
    private int dataId;

    private SceneManager() { }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void initialize(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LOGIN_SCENE));
            Parent root = fxmlLoader.load();
            this.setWindow(primaryStage);

            primaryStage.setTitle("Appointment Manager");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            System.out.print("An error occurred while attempting to initialize scenes" + System.lineSeparator() + e.getMessage());
        }
    }

    public void goToScene(String fxml) {
        goToScene(fxml, -1);
    }

    public void goToScene(String fxml, int id) {
        try {
            this.setDataId(id);
            
            Parent main = FXMLLoader.load(getClass().getResource(fxml));

            borderPane.setCenter(main);
            window.centerOnScreen();
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

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int id) {
        this.dataId = id;
    }

    private void setWindow(Stage window) {
        this.window = window;
    }
}
