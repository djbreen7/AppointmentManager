package managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    public static final String LOGIN_SCENE = "../application/login/login.fxml";
    public static final String APPOINTMENTS_SCENE = "../application/appointments/appointments.fxml";

    private static SceneManager instance = null;
    private Stage window;

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
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(parent);

            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println("An Error occurred when attempting to change scenes");
        }
    }

    public void setWindow(Stage window) {
        this.window = window;
    }
}
