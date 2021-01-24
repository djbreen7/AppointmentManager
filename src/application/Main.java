package application;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
      var sceneManager =  SceneManager.getInstance();
      sceneManager.initialize(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
