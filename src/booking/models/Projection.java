
package booking.models;

import booking.DatabaseManager;
import java.util.Date;

/**
 * A class that represents a projection of a movie.
 *
 */
public class Projection {

    private int id, movieId, roomId, booked;
    private Room room;
    private Movie movie;
    private Date date;
    private int price;

    /**
     * Default constructor
     */
    public Projection() {
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
    public int getMovieId() {
        return movieId;
    }

    /**
     *
     * @param movieId
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     *
     * @return
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     *
     * @param roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     *
     * @return
     */
    public int getBooked() {
        return booked;
    }

    /**
     *
     * @param booked
     */
    public void setBooked(int booked) {
        this.booked = booked;
    }

    /**
     *
     * @return
     */
    public Room getRoom() {
        return room;
    }

    /**
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     *
     * @return
     */
    public Movie getMovie() {
        if (movie == null) {
            movie = DatabaseManager.getInstance().getMovie(movieId);
        }
        return movie;
    }

    /**
     *
     * @param movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
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
    
    public String getMovieTitle() {
        return getMovie().getTitle();
    }
    
    /**
     *
     * @return
     */
    public String getRoomName() {
        return getRoom().getName();
    }

}
