package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseSceneController {
    protected static Stage window;

    protected final static String MAIN_PATH = "./main.fxml";
//    protected final static String UPSERT_PART_SCREEN_PATH = "./UpsertPart.fxml";
//    protected final static String UPSERT_PRODUCT_SCREEN_PATH = "./UpsertProduct.fxml";

    protected void goToScene(String fxml) {
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
