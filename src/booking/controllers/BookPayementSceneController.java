package booking.controllers;

import booking.DatabaseManager;
import booking.Main;
import booking.SendEmail;
import booking.models.AuthenticatedUser;
import booking.models.Booking;
import booking.models.Customer;
import booking.models.Guest;
import booking.models.PromoCode;
import booking.models.User;
import java.io.IOException;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class BookPayementSceneController {

    @FXML
    Button validateButton;
    @FXML
    TextField firstnameText, lastnameText, emailText, cardnumberText, usernameText, passwordText, promoCodeText;
    @FXML
    Label totalLabel;

    private List<Booking> bookings;

    @FXML
    void initialize() throws IOException {

        initScene();

    }

    /**
     * Initialize the scene
     */
    public void initScene() {
        firstnameText.setDisable(Main.getCurrentUser() instanceof Customer);
        lastnameText.setDisable(Main.getCurrentUser() instanceof Customer);
        emailText.setDisable(Main.getCurrentUser() instanceof Customer);
        usernameText.setDisable(Main.getCurrentUser() instanceof Customer);
        passwordText.setDisable(Main.getCurrentUser() instanceof Customer);
        if (Main.getCurrentUser() instanceof Customer) {
            firstnameText.setText(Main.getCurrentUser().getFirstName());
            lastnameText.setText(Main.getCurrentUser().getLastName());
            emailText.setText(Main.getCurrentUser().getEmail());
        } else if (Main.getCurrentUser() instanceof Guest) {

        }
    }

    /**
     *
     * @param bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        int total = bookings.get(0).getProjection().getPrice() * bookings.size();
        totalLabel.setText(total + " Euros");
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionValidate(ActionEvent event) throws IOException {
        if (firstnameText.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert");
            alert.setContentText("First name not valid!!");
            alert.showAndWait();
            return;
        } else if (lastnameText.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert");
            alert.setContentText("Last name not valid!!");
            alert.showAndWait();
            return;
        } else if (emailText.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert");
            alert.setContentText("Email not valid!!");
            alert.showAndWait();
            return;
        } else if (cardnumberText.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert");
            alert.setContentText("Card number not valid!!");
            alert.showAndWait();
            return;
        }

        User user = null;
        if (Main.getCurrentUser() instanceof AuthenticatedUser) {
            user = Main.getCurrentUser();
        } else if (Main.getCurrentUser() instanceof Guest) {
            user = new User();
            user.setEmail(emailText.getText());
            user.setFirstName(firstnameText.getText());
            user.setLastName(lastnameText.getText());
            user.setPassword(passwordText.getText());
            user.setUsername(usernameText.getText());
            if (!usernameText.getText().trim().isEmpty() && !passwordText.getText().trim().isEmpty()) {
                user.setType("CUSTOMER");
            } else {
                user.setType("GUEST");
            }

            user = user.save();
        }

        if (user != null) {
            int discount = 0;
            PromoCode promo = DatabaseManager.getInstance().getPromoCode(promoCodeText.getText().trim());
            if (promo != null) {
                discount = promo.getDiscount();
            }
            for (Booking booking : bookings) {
                booking.setUserId(user.getId());
                booking.setTransaction("32234234");
                booking.setPrice(booking.getProjection().getPrice() - discount);
                booking.save();
                SendEmail.sendEmail(booking);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking");
            alert.setHeaderText("Booking");
            alert.setContentText("Booking savec succesfully!!");
            alert.showAndWait();
            Stage stage = (Stage) validateButton.getScene().getWindow();
            stage.close();
        }

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionApplyClick(MouseEvent event) throws IOException {
        PromoCode promo = DatabaseManager.getInstance().getPromoCode(promoCodeText.getText().trim());
        if (promo != null) {
            int total = bookings.get(0).getProjection().getPrice() * bookings.size();
            totalLabel.setText(total - bookings.size() * promo.getDiscount() + "");
        }
    }

}
