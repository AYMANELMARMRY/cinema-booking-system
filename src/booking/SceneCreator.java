package booking;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class that creates new scenes. This class is inherited every time a new scene is launched.
 * @author Mohamed Ayman
 */
public class SceneCreator {

    // launching the new scene based on the .fxml file name passed in the argument as a String variable
    // building the scene and setting the value for the instance variable loader

    /**
     *
     * @param sceneName
     * @throws IOException
     */
    public static void launchScene (String sceneName) throws IOException {

        // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
        Main.setRoot(loader.load());
        Scene scene = new Scene(Main.getRoot());
        Main.getStage().setScene(scene);
        Main.getStage().setResizable(true);
        Main.getStage().setMaximized(true);
        Main.getStage().show();
    }
    
    /**
     *
     * @param stage
     * @param sceneName
     * @return
     * @throws IOException
     */
    public static Object changeScene (Stage stage, String sceneName) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(sceneName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return loader.getController();
        
    }
    
    
}