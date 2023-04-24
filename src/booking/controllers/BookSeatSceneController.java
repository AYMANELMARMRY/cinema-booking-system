package booking.controllers;

import booking.DatabaseManager;
import booking.SceneCreator;
import booking.models.Booking;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class BookSeatSceneController {

    @FXML
    Button validateButton;
    @FXML
    TilePane seatsPane;

    private Booking booking;

    @FXML
    void initialize() throws IOException {

    }

    /**
     *
     * @param bookings
     * @param i
     * @return
     */
    public boolean isAvailable(List<Booking> bookings, int i){
        for(Booking b: bookings){
            if(b.getSeat()==i){
                return false;
            }
        }
        return true;
    }
    
    /**
     *
     * @param booking
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
        List<Booking> bookings = DatabaseManager.getInstance().getBookings(booking.getProjection());
        
        for (int i = 0; i < booking.getProjection().getRoom().getSeats(); i++) {
            VBox vBox = new VBox();
            ImageView imageView = new ImageView();
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            CheckBox check = new CheckBox();
            check.setTranslateX(13);
            check.setUserData(i);
            
            if(isAvailable(bookings, i)){
                imageView.setImage(new Image("/images/available.png"));
                check.setSelected(false);
            }else{
                imageView.setImage(new Image("/images/busy.png"));
                check.setSelected(true);
                check.setDisable(true);
            }
            
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(check);
            seatsPane.getChildren().add(vBox);
        }
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionPayement(MouseEvent event) throws IOException {
        
        
        List<Booking> newBookings = new ArrayList<>();
        for (int i = 0; i < seatsPane.getChildren().size(); i++) {
            VBox box = (VBox)seatsPane.getChildren().get(i);
            Node node = box.getChildren().get(1);
            if(!((CheckBox)node).isDisable() && ((CheckBox)node).isSelected()){
                Booking b = new Booking();
                b.setProjection(this.booking.getProjection());
                b.setProjectionId(this.booking.getProjection().getId());
                b.setSeat(Integer.parseInt(node.getUserData().toString()));
                newBookings.add(b);
            }
        }
        
        BookPayementSceneController controller =  (BookPayementSceneController)SceneCreator.changeScene((Stage)((Node)event.getSource()).getScene().getWindow(), "/scenes/BookPayementScene.fxml");
        controller.setBookings(newBookings);
        
        
    }   
    

}
