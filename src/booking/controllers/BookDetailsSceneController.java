package booking.controllers;

import booking.DatabaseManager;
import booking.Main;
import booking.SceneCreator;
import booking.models.Booking;
import java.io.IOException;

import booking.models.Movie;
import booking.models.Projection;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class BookDetailsSceneController {

    @FXML
    Button projectionButton;
    @FXML
    VBox vBoxList;

    private Movie movie;

    @FXML
    void initialize() throws IOException {

    }

    /**
     *
     * @param movie
     */
    public void setMovie(Movie movie) {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm");
        this.movie = movie;
        List<Projection> projections = DatabaseManager.getInstance().getProjections(movie);

        for (Projection projection : projections) {
            RadioButton radioButton = new RadioButton();
            radioButton.setText("Movie : " + projection.getMovie().getTitle() + " Date : " + format.format(projection.getDate()) + " Room : " + projection.getRoom().getName() + " Available : " + (projection.getRoom().getSeats() - projection.getBooked()));
            vBoxList.getChildren().add(radioButton);
            radioButton.setUserData(projection);
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionNext(MouseEvent event) throws IOException {

        Booking booking = new Booking();
        boolean found = false;
        for (int i = 0; i < vBoxList.getChildren().size(); i++) {
            RadioButton radio = (RadioButton) vBoxList.getChildren().get(i);
            if (radio.isSelected()) {
                booking.setProjection((Projection) radio.getUserData());
                found = true;
                break;
            }
        }
        if (found) {
            BookSeatSceneController controller = (BookSeatSceneController) SceneCreator.changeScene((Stage) ((Node) event.getSource()).getScene().getWindow(), "/scenes/BookSeatScene.fxml");
            controller.setBooking(booking);
        } else {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setTitle("Error alert");
            alert.setHeaderText("No projection selection");
            alert.setContentText("Please select a projection to book!");

            alert.showAndWait();

        }

    }

}
