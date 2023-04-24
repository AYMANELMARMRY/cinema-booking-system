package booking.models;

import booking.DatabaseManager;
import java.text.SimpleDateFormat;

/**
 * A class reprensenting a booking.
 *
 */

public class Booking {

    private int id;
    private int userId;
    private int projectionId;
    private Projection projection;
    private int seat;
    private String transaction;
    private int price;
    
    /**
     * Default constructor
     */
    public Booking() {
    }

    /**
     *
     * @return
     */
    public int getSeat() {
        return seat;
    }

    /**
     *
     * @param seat
     */
    public void setSeat(int seat) {
        this.seat = seat;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getProjectionId() {
        return projectionId;
    }

    /**
     *
     * @param projectionId
     */
    public void setProjectionId(int projectionId) {
        this.projectionId = projectionId;
    }

    /**
     *
     * @return
     */
    public Projection getProjection() {
        if(projection==null){
            projection = DatabaseManager.getInstance().getProjection(projectionId);
        }
        return projection;
    }

    /**
     *
     * @param projection
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /**
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getTransaction() {
        return transaction;
    }

    /**
     *
     * @param transaction
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    /**
     * Save booking object
     */
    public void save() {
        DatabaseManager.getInstance().saveBooking(this);
    }

    /**
     *
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
    // For TableView

    /**
     *
     * @return
     */
    public String getMovieTitle(){
        return getProjection().getMovie().getTitle();
    }

    /**
     *
     * @return
     */
    public String getMovieDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(getProjection().getDate());
    }

    /**
     *
     * @return
     */
    public String getMovieTime(){
        return new SimpleDateFormat("HH:mm").format(getProjection().getDate());
    }
}