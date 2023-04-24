package booking;

import booking.models.Booking;
import javafx.scene.control.Alert;

/**
 *
 * @author Mohamed Ayman
 */
public class Util {

    /**
     *
     * @return
     */
    public static String[] getGenders(){
		return new String[]{"Comedy","Action","Science fiction","Drama","Documentary","Bibliography"};
	}
        
    /**
     *
     * @param message
     */
    public static void showErrorMessage(String message){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        }
        
    /**
     *
     * @param booking
     */
    public static void PrintTicket(Booking booking){
            
        }
	
}
