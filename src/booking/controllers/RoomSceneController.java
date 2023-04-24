package booking.controllers;

import booking.DatabaseManager;
import booking.Util;
import java.io.IOException;

import booking.models.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The controller for the User Scene.
 *
 * @author Mohamed et Ayman
 * @since 
 */
public class RoomSceneController {

    @FXML
    Button saveRoomButton;
    @FXML
    TextField roomnameText, seatsText;

    private Room room;

    @FXML
    void initialize() throws IOException {

    }

    /**
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
        if (room != null) {
            roomnameText.setText(room.getName());
            seatsText.setText(room.getSeats()+"");
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void actionSaveRoom(MouseEvent event) throws IOException {
        if(!validateForm()){
           return; 
        }
        if (room == null) {
            room = new Room();
        }
        room.setName(roomnameText.getText());
        room.setSeats(Integer.parseInt(seatsText.getText()));
        if (room.getId() == 0) {
            DatabaseManager.getInstance().addRoom(room);
        } else {
            DatabaseManager.getInstance().updateRoom(room);
        }
        ((Stage)saveRoomButton.getScene().getWindow()).close();
    }
    
    /**
     *
     * @return
     */
    public boolean validateForm(){
        
        if(roomnameText.getText().trim().isEmpty()){
            Util.showErrorMessage("Give a name to the room");
            return false;
        }
        
        try{
            Integer.parseInt(seatsText.getText());
        }catch(Exception e){
            Util.showErrorMessage("Seats must be a number");
            return false;
        }
        
        return true;
    }

}
