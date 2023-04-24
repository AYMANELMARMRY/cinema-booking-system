package booking.models;

import booking.DatabaseManager;
import booking.Main;
import java.util.List;

/**
 * A class that represents an authenticated user.
 *
 */
public class AuthenticatedUser extends User {

    /**
     * To logout from system
     */
    public void logout() {
        Main.logOut();
    }
    
    /**
     *
     * @return list of current user booking history
     */
    public List<Booking> getMyBookings(){
        return DatabaseManager.getInstance().getBookings(this);
    }

}
