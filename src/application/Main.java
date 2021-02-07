package application;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.SceneManager;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    
    /** 
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
      var sceneManager =  SceneManager.getInstance();
      sceneManager.initialize(primaryStage);
    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
