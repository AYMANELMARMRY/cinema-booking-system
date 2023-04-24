package booking.controllers;

import booking.DatabaseManager;
import booking.SceneCreator;
import booking.Util;
import java.io.IOException;

import booking.models.Movie;
import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class MovieSceneController {

    @FXML
    Button saveMovieButton;
    @FXML
    TextField movietitleText, movieyearText, movieposterText, movietrailerText, movielengthText, moviegenderText;
    @FXML
    TextArea moviedescriptionTextArea;

    private Movie movie;

    @FXML
    void initialize() throws IOException {

    }

    /**
     *
     * @param movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
        if (movie != null) {
            movietitleText.setText(movie.getTitle());
            moviegenderText.setText(movie.getGender());
            movieyearText.setText(movie.getYear() + "");
            movielengthText.setText(movie.getLength() + "");
            moviedescriptionTextArea.setText(movie.getDescription());
            movieposterText.setText(movie.getPoster());
            movietrailerText.setText(movie.getTrailer());
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionSaveMovie(MouseEvent event) throws IOException {
        
        if(!validateForm()){
            return;
        }
        
        if (movie == null) {
            movie = new Movie();
        }
        movie.setTitle(movietitleText.getText());
        movie.setDescription(moviedescriptionTextArea.getText());
        movie.setGender(moviegenderText.getText());
        movie.setLength(Integer.parseInt(movielengthText.getText()));
        movie.setPoster(movieposterText.getText());
        movie.setTrailer(movietrailerText.getText());
        movie.setYear(Integer.parseInt(movieyearText.getText()));
        if (movie.getId() == 0) {
            DatabaseManager.getInstance().addMovie(movie);
        } else {
            DatabaseManager.getInstance().updateMovie(movie);
        }
        ((Stage)saveMovieButton.getScene().getWindow()).close();
    }
    
    /**
     *
     * @return
     */
    public boolean validateForm(){
        
        if(movietitleText.getText().trim().isEmpty()){
            Util.showErrorMessage("Give a title to the movie");
            return false;
        }
        
        try{
            Integer.parseInt(movielengthText.getText());
        }catch(Exception e){
            Util.showErrorMessage("Length must be a number");
            return false;
        }
        
        return true;
    }

}
