package booking;

import booking.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for cinema booking application.
 *
 * @author Mohamed Ayman
 * @since 
 *
 */
public class Main extends Application {

    static Parent root;
    static Stage primaryStage;
    static Main main = null;
    static User currentUser;

    /**
     * The main method.It checks whether the designed files exist.If not, it
 generates them. Then, the first scene is launched.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);

    }

    /**
     *
     * @return
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @param currentUser
     */
    public static void setCurrentUser(User currentUser) {
        Main.currentUser = currentUser;
    }

    /**
     *
     * @return
     */
    public static Parent getRoot() {
        return root;
    }

    static void setRoot(Parent root) {
        Main.root = root;
    }

    /**
     *
     * @return
     */
    public static Stage getStage() {
        return primaryStage;
    }

    static void setStage(Stage stage) {
        Main.primaryStage = stage;
    }

    /**
     * The method that kicks off the first scene of our application, the
     * LoginScene.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // setting up the login scene
            root = FXMLLoader.load(getClass().getResource("/scenes/LoginScene.fxml"));
            Main.primaryStage = primaryStage;
            primaryStage.setTitle("Cinema Booking System");
            primaryStage.setResizable(false);
            Scene scene = new Scene(root, 680, 350);
            scene.getStylesheets().add(getClass().getResource("/scenes/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logout from UI
     */
    public static void logOut() {
        try {
            primaryStage = new Stage();
            setCurrentUser(null);
            root = FXMLLoader.load(Main.class.getResource("/scenes/LoginScene.fxml"));
            primaryStage.setResizable(false);
            Scene scene = new Scene(root, 680, 350);
            scene.getStylesheets().add(Main.class.getResource("/scenes/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
