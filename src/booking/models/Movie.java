package booking.models;

/**
 * A class represeting a movie.
 *
 */
public class Movie {

    private String title = "", description = "", trailer = "", gender = "", poster = "";
    private int id, length, year;

    /**
     * Default constructor
     */
    public Movie() {

    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     *
     * @param trailer
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public String getPoster() {
        return poster;
    }

    /**
     *
     * @param poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
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
    public int getLength() {
        return length;
    }

    /**
     *
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return title;
    }

}
