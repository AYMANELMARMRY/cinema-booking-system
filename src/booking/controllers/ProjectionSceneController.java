package booking.controllers;

import booking.DatabaseManager;
import booking.models.Movie;
import java.io.IOException;

import booking.models.Projection;
import booking.models.Room;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class ProjectionSceneController {

    @FXML
    Button saveProjectionButton;
    @FXML
    TextField priceText;
    @FXML
    DatePicker datePicker;
    @FXML
    ComboBox<Movie> moviesCombo;
    @FXML
    ComboBox<Room> roomsCombo;
    @FXML
    ComboBox hoursCombo, minutesCombo;

    private Projection projection;

    @FXML
    void initialize() throws IOException {

        initScene();

    }

    /**
     * Initialize Scene
     */
    public void initScene() {
        ObservableList<Movie> data = FXCollections.observableArrayList(DatabaseManager.getInstance().getMovies("", ""));
        moviesCombo.setItems(data);
        ObservableList<Room> data2 = FXCollections.observableArrayList(DatabaseManager.getInstance().getRooms());
        roomsCombo.setItems(data2);
        for (int i = 0; i < 23; i++) {
            hoursCombo.getItems().add(i+"");
        }
        for (int i = 0; i < 59; i++) {
            minutesCombo.getItems().add(i+"");
        }
    }

    /**
     *
     * @param projection
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
        if (projection != null) {
            priceText.setText(projection.getPrice()+"");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(projection.getDate());
            datePicker.setValue(LocalDate.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)));
            moviesCombo.setValue(projection.getMovie());
            roomsCombo.setValue(projection.getRoom());
            minutesCombo.setValue(calendar.get(Calendar.MINUTE));
            hoursCombo.setValue(calendar.get(Calendar.HOUR));
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionSaveProjection(MouseEvent event) throws IOException {
        if (projection == null) {
            projection = new Projection();
            projection.setMovie(moviesCombo.getValue());
            projection.setRoom(roomsCombo.getValue());
            projection.setPrice(Integer.parseInt(priceText.getText()));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getValue().getYear());
            calendar.set(Calendar.MONTH, datePicker.getValue().getMonthValue());
            calendar.set(Calendar.DATE, datePicker.getValue().getDayOfMonth());
            calendar.set(Calendar.HOUR, Integer.parseInt(hoursCombo.getValue().toString()));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minutesCombo.getValue().toString()));
            
            projection.setDate(calendar.getTime());
        }
        projection.setPrice(Integer.parseInt(priceText.getText()));
        
        if (projection.getId() == 0) {
            DatabaseManager.getInstance().addProjection(projection);
        } else {
            DatabaseManager.getInstance().updateProjection(projection);
        }
        ((Stage) saveProjectionButton.getScene().getWindow()).close();
    }

}
