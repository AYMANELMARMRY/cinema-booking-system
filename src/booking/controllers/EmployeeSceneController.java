package booking.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import booking.DatabaseManager;
import booking.Main;
import booking.Util;
import booking.models.AuthenticatedUser;
import booking.models.Booking;
import booking.models.Movie;
import booking.models.Projection;
import booking.models.Room;
import booking.models.User;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class EmployeeSceneController {

    @FXML
    Button logoutButton, myBookingsButton, editButton;
    @FXML
    TextField firstnameText, lastnameText, emailText, oldpasswordText, newpasswordText;
    @FXML
    Pane moviesPane, bookingPane, profilePane, projectionsPane, roomsPane;
    @FXML
    TableView bookingsTable, moviesTable, projectionsTable, roomsTable;

    List<ImageView> moviesImages = new ArrayList<>();

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
     * Initialize Movies Scene
     */
    public void initSceneMovies() {

        moviesPane.setVisible(true);
        profilePane.setVisible(false);
        bookingPane.setVisible(false);
        projectionsPane.setVisible(false);
        roomsPane.setVisible(false);

        moviesTable.getColumns().clear();
        String[] columns = new String[]{"Title", "Year", "Gender", "Length"};
        String[] names = new String[]{"title", "year", "gender", "length"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = new TableColumn(columns[i]);
            col.setMinWidth(200);
            col.setCellValueFactory(new PropertyValueFactory<>(names[i]));
            moviesTable.getColumns().add(col);
        }
        ObservableList<Movie> data = FXCollections.observableArrayList(DatabaseManager.getInstance().getMovies("", ""));
        moviesTable.setItems(data);

    }

    /**
     * Initialize Booking History Scene
     */
    public void initSceneBookingHistory() {
        moviesPane.setVisible(false);
        profilePane.setVisible(false);
        bookingPane.setVisible(true);
        projectionsPane.setVisible(false);
        roomsPane.setVisible(false);

        bookingsTable.getColumns().clear();
        String[] columns = new String[]{"Title", "Date", "Time", "Seat"};
        String[] names = new String[]{"MovieTitle", "MovieDate", "MovieTime", "Seat"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = new TableColumn(columns[i]);
            col.setMinWidth(200);
            col.setCellValueFactory(new PropertyValueFactory<>(names[i]));
            bookingsTable.getColumns().add(col);
        }
        ObservableList<Booking> data = FXCollections.observableArrayList(DatabaseManager.getInstance().getBookings());
        bookingsTable.setItems(data);
    }

    /**
     * Initialize Profile Scene
     */
    public void initSceneProfile() {
        moviesPane.setVisible(false);
        profilePane.setVisible(true);
        bookingPane.setVisible(false);
        projectionsPane.setVisible(false);
        roomsPane.setVisible(false);
        
        firstnameText.setText(Main.getCurrentUser().getFirstName());
        lastnameText.setText(Main.getCurrentUser().getLastName());
        emailText.setText(Main.getCurrentUser().getEmail());
    }
    
    /**
     * Initialize Projections List Scene
     */
    public void initSceneProjections(){
        
        moviesPane.setVisible(false);
        profilePane.setVisible(false);
        bookingPane.setVisible(false);
        projectionsPane.setVisible(true);
        roomsPane.setVisible(false);
        
        projectionsTable.getColumns().clear();
        String[] columns = new String[]{"ID", "Movie", "Date", "Room", "Booked", "Price"};
        String[] names = new String[]{"Id", "MovieTitle", "Date", "RoomName", "Booked", "Price"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = new TableColumn(columns[i]);
            col.setMinWidth(180);
            col.setCellValueFactory(new PropertyValueFactory<>(names[i]));
            projectionsTable.getColumns().add(col);
        }
        ObservableList<Projection> data = FXCollections.observableArrayList(DatabaseManager.getInstance().getProjections());
        projectionsTable.setItems(data);
    }
    
    /**
     * Initialize Rooms Scene
     */
    public void initSceneRooms(){
        
        moviesPane.setVisible(false);
        profilePane.setVisible(false);
        bookingPane.setVisible(false);
        projectionsPane.setVisible(false);
        roomsPane.setVisible(true);
        
        roomsTable.getColumns().clear();
        String[] columns = new String[]{"Name", "Seats"};
        String[] names = new String[]{"Name", "Seats"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn col = new TableColumn(columns[i]);
            col.setMinWidth(180);
            col.setCellValueFactory(new PropertyValueFactory<>(names[i]));
            roomsTable.getColumns().add(col);
        }
        ObservableList<Room> data = FXCollections.observableArrayList(DatabaseManager.getInstance().getRooms());
        roomsTable.setItems(data);
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
    public void actionManageProjections(MouseEvent event) throws IOException {
        initSceneProjections();
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionManageRooms(MouseEvent event) throws IOException {
        initSceneRooms();
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

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionAddMovie(MouseEvent event) throws IOException {
        Stage dialog = new Stage();

        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("/scenes/MovieScene.fxml"));
            root = loader.load();
            MovieSceneController controller = loader.getController();
            controller.setMovie(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.initOwner(Main.getStage());
        dialog.setResizable(false);
        dialog.setWidth(700);
        dialog.setHeight(450);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.showAndWait();
        initSceneMovies();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionEditMovie(MouseEvent event) throws IOException {
        Movie movie = (Movie) moviesTable.getSelectionModel().getSelectedItem();
        if (movie != null) {
            Stage dialog = new Stage();
            Parent root = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(Main.class.getResource("/scenes/MovieScene.fxml"));
                root = loader.load();
                MovieSceneController controller = loader.getController();
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
            initSceneMovies();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a movie!!");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionDeleteMovie(MouseEvent event) throws IOException {
        Movie movie = (Movie) moviesTable.getSelectionModel().getSelectedItem();
        if (movie != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure to delete this movie!!");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                DatabaseManager.getInstance().delete(movie);
                initSceneMovies();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a movie!!");
            alert.showAndWait();
        }
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionAddProjection(MouseEvent event) throws IOException {
        Stage dialog = new Stage();

        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("/scenes/ProjectionScene.fxml"));
            root = loader.load();
            ProjectionSceneController controller = loader.getController();
            controller.setProjection(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.initOwner(Main.getStage());
        dialog.setResizable(false);
        dialog.setWidth(700);
        dialog.setHeight(450);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.showAndWait();
        initSceneProjections();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionEditProjection(MouseEvent event) throws IOException {
        Projection projection = (Projection) projectionsTable.getSelectionModel().getSelectedItem();
        if (projection != null) {
            Stage dialog = new Stage();
            Parent root = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(Main.class.getResource("/scenes/ProjectionScene.fxml"));
                root = loader.load();
                ProjectionSceneController controller = loader.getController();
                controller.setProjection(projection);
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
            initSceneProjections();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a projection!!");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionDeleteProjection(MouseEvent event) throws IOException {
        Projection projection = (Projection) projectionsTable.getSelectionModel().getSelectedItem();
        if (projection != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure to delete this projection!!");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                DatabaseManager.getInstance().delete(projection);
                initSceneProjections();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a projection!!");
            alert.showAndWait();
        }
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionAddRoom(MouseEvent event) throws IOException {
        Stage dialog = new Stage();

        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("/scenes/RoomScene.fxml"));
            root = loader.load();
            RoomSceneController controller = loader.getController();
            controller.setRoom(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.initOwner(Main.getStage());
        dialog.setResizable(false);
        dialog.setWidth(700);
        dialog.setHeight(450);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.showAndWait();
        initSceneRooms();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionEditRoom(MouseEvent event) throws IOException {
        Room room = (Room) roomsTable.getSelectionModel().getSelectedItem();
        if (room != null) {
            Stage dialog = new Stage();
            Parent root = null;
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(Main.class.getResource("/scenes/RoomScene.fxml"));
                root = loader.load();
                RoomSceneController controller = loader.getController();
                controller.setRoom(room);
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
            initSceneRooms();
        } else {
            Util.showErrorMessage("Select a room!");
        }
    }
    
}


