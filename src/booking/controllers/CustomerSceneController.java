package booking.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import booking.DatabaseManager;
import booking.Main;
import booking.models.AuthenticatedUser;
import booking.models.Booking;
import booking.models.Movie;
import booking.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class CustomerSceneController {

    @FXML
    Button logoutButton, myBookingsButton, editButton;
    @FXML
    TextField searchText, firstnameText, lastnameText, emailText, oldpasswordText, newpasswordText;
    @FXML
    TilePane gridPane;
    @FXML
    Pane moviesPane, bookingPane, profilePane;
    @FXML
    TableView bookingsTable;

    List<ImageView> moviesImages = new ArrayList<ImageView>();

    @FXML
    void initialize() throws IOException {

        initSceneMovies();

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void logOutClick(ActionEvent event) throws IOException {
        Main.logOut();
    }

    /**
     * Initialize Movies scene
     */
    public void initSceneMovies() {

        logoutButton.setVisible(Main.getCurrentUser() instanceof AuthenticatedUser);
        myBookingsButton.setVisible(Main.getCurrentUser() instanceof AuthenticatedUser);
        editButton.setVisible(Main.getCurrentUser() instanceof AuthenticatedUser);

        moviesPane.setVisible(true);
        profilePane.setVisible(false);
        bookingPane.setVisible(false);

        gridPane.getChildren().removeAll(gridPane.getChildren());

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setPrefColumns(9);

        List<Movie> movies = DatabaseManager.getInstance().getMovies("", "");

        for (int i = 0; i < movies.size(); i++) {
            ImageView imageView;
            imageView = createImageView(movies.get(i));
            gridPane.getChildren().addAll(imageView);
        }

    }

    private ImageView createImageView(Movie movie) {
        ImageView imageView = null;
        final Image image = new Image(movie.getPoster(), true);
        imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(200);
        imageView.setStyle("-fx-cursor: hand;");
        imageView.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Stage dialog = new Stage();

                Parent root = null;
                FXMLLoader loader = null;
                try {
                    loader = new FXMLLoader(Main.class.getResource("/scenes/BookScene.fxml"));
                    root = loader.load();
                    BookSceneController controller = loader.getController();
                    controller.setMovie(movie);
                } catch (IOException e) {
                }
                Scene scene = new Scene(root);
                dialog.setScene(scene);
                dialog.initOwner(Main.getStage());
                dialog.setResizable(false);
                dialog.setWidth(700);
                dialog.setHeight(450);
                dialog.initModality(Modality.APPLICATION_MODAL);

                dialog.showAndWait();
            }
        ;
        });
		return imageView;
    }

    /**
     * Initialize Book History scene
     */
    public void initSceneBookingHistory() {
        moviesPane.setVisible(false);
        profilePane.setVisible(false);
        bookingPane.setVisible(true);
        bookingsTable.getColumns().clear();
        String[] columns = new String[]{"Title", "Date", "Time", "Seat"};
        String[] names = new String[]{"MovieTitle", "MovieDate", "MovieTime", "Seat"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = new TableColumn(columns[i]);
            col.setMinWidth(150);
            col.setCellValueFactory(new PropertyValueFactory<>(names[i]));
            bookingsTable.getColumns().add(col);
        }
        ObservableList<Booking> data = FXCollections.observableArrayList(((AuthenticatedUser) Main.getCurrentUser()).getMyBookings());
        bookingsTable.setItems(data);
    }

    /**
     * Initialize Profile scene
     */
    public void initSceneProfile() {
        moviesPane.setVisible(false);
        profilePane.setVisible(true);
        bookingPane.setVisible(false);
        firstnameText.setText(Main.getCurrentUser().getFirstName());
        lastnameText.setText(Main.getCurrentUser().getLastName());
        emailText.setText(Main.getCurrentUser().getEmail());
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionBrowseMovies(MouseEvent event) throws IOException {
        initSceneMovies();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionBookingHistory(MouseEvent event) throws IOException {
        initSceneBookingHistory();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionEditProfile(MouseEvent event) throws IOException {
        initSceneProfile();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionLogout(MouseEvent event) throws IOException {
        ((AuthenticatedUser) Main.getCurrentUser()).logout();
        ((Stage) logoutButton.getScene().getWindow()).close();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionSaveProfile(ActionEvent event) throws IOException {
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
        }

        if (!oldpasswordText.getText().trim().isEmpty() || !newpasswordText.getText().trim().isEmpty()) {
            if (!oldpasswordText.getText().trim().equals(Main.getCurrentUser().getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error alert");
                alert.setContentText("Password not valid!!");
                alert.showAndWait();
                return;
            } else if (newpasswordText.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error alert");
                alert.setContentText("New password not valid!!");
                alert.showAndWait();
                return;
            }
        }

        User user = Main.getCurrentUser();
        user.setEmail(emailText.getText());
        user.setFirstName(firstnameText.getText());
        user.setLastName(lastnameText.getText());
        if (!oldpasswordText.getText().trim().isEmpty() || !newpasswordText.getText().trim().isEmpty()) {
            user.setPassword(newpasswordText.getText());
        }
        user = user.save();
        Main.setCurrentUser(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Profile saved!!");
        alert.showAndWait();

    }

}
