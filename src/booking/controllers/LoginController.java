package booking.controllers;

import java.io.*;
import java.security.GeneralSecurityException;

import booking.DatabaseManager;
import booking.Main;
import booking.SceneCreator;
import booking.models.Guest;
import booking.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The controller for the Login Scene.
 *
 * @author Mohamed Ayman
 */
public class LoginController {

    @FXML
    TextField usernameText;
    @FXML
    PasswordField passwordText;
    @FXML
    Button logInButton, logOutButton;
    @FXML
    Text messageText;

    /**
     *
     * @param event
     */
    @FXML
    public void exitButton(MouseEvent event) {
        System.exit(0);
    }

    /**
     * A method that handles the login procedure for all kinds of users
     *
     * @param event event
     * @throws java.io.IOException
     * @throws java.security.GeneralSecurityException
     */
    @FXML
    public void loginClick(ActionEvent event) throws IOException, GeneralSecurityException {

        messageText.setVisible(false);
        User user = DatabaseManager.getInstance().getUser(usernameText.getText(), passwordText.getText());
        if (user != null) {

            Main.setCurrentUser(user);

            if (user.getType().equals(User.TYPE_CUSTOMER)) {

                SceneCreator.launchScene("/scenes/CustomerScene.fxml");

            } else if (user.getType().equals(User.TYPE_EMPLOYE)) {

                SceneCreator.launchScene("/scenes/EmployeeScene.fxml");

            }
        } else {
            messageText.setVisible(true);
        }

    }

    /**
     *
     * @param event
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @FXML
    public void loginGuestClick(ActionEvent event) throws IOException, GeneralSecurityException {

        messageText.setVisible(false);
        User user = new Guest();

        Main.setCurrentUser(user);

        SceneCreator.launchScene("/scenes/CustomerScene.fxml");
    }
}
