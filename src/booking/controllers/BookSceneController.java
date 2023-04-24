package booking.controllers;

import booking.SceneCreator;
import java.io.IOException;

import booking.models.Movie;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class BookSceneController {

    @FXML
    Button bookButton, validateButton;
    @FXML
    Label titleLabel, genderLabel, yearLabel, lengthLabel, descriptionLabel;
    @FXML
    ImageView posterImageView;
    @FXML
    VBox vBoxList;
    @FXML
    Hyperlink trailerLink;

    private Movie movie;

    @FXML
    void initialize() throws IOException {
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionBook(MouseEvent event) throws IOException {
        BookDetailsSceneController controller =  (BookDetailsSceneController)SceneCreator.changeScene((Stage)((Node)event.getSource()).getScene().getWindow(), "/scenes/BookDetailsScene.fxml");
        controller.setMovie(movie);
    }
    
    /**
     *
     * @param event
     */
    @FXML
    public void actionOpenTrailer(MouseEvent event){
        try {
            Desktop.getDesktop().browse(new URI(movie.getTrailer()));
        } catch (Exception ex) {
            Logger.getLogger(BookSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
        titleLabel.setText(movie.getTitle().toUpperCase());
        genderLabel.setText(movie.getGender().toUpperCase());
        yearLabel.setText(movie.getYear() + "");
        lengthLabel.setText(movie.getLength() + " min");
        descriptionLabel.setText(movie.getDescription());
        posterImageView.setImage(new Image(movie.getPoster(), true));
    }

}
