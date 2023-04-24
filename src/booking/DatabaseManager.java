package booking;

import booking.models.Booking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import booking.models.Customer;
import booking.models.Employee;
import booking.models.Movie;
import booking.models.Projection;
import booking.models.PromoCode;
import booking.models.Room;
import booking.models.User;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is offering static methods to manage database
 * @author Mohamed et Ayman
 */
public class DatabaseManager {

    private static DatabaseManager databaseManager;
    private String host = "23.81.206.207", port = "3306", database = "booking-cinema", username = "booking", password = "booking123";
    private Connection connection;

    private DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?"
                    + "user=" + username + "&password=" + password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    /**
     * Get user by username and password.
     *
     * @param username The username.
     * @param password The password.
     *
     * @return The user of given username and password if exists otherwise null.
     *
     */
    public User getUser(String username, String password) {
        try {
            PreparedStatement stm = connection.prepareStatement("select * from users where username=? and password=?");
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                User user = null;
                if (type.equals(User.TYPE_CUSTOMER)) {
                    user = new Customer();
                } else if (type.equals(User.TYPE_EMPLOYE)) {
                    user = new Employee();
                }

                user.setId(rs.getInt("id"));
                user.setType(rs.getString("type"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Room by id.
     *
     * @param id The id of room.
     *
     * @return The room of given id if exists otherwise null.
     *
     */
    public Room getRoom(int id) {
        Room room = new Room();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from rooms where id=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSeats(rs.getInt("seats"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    /**
     * Get all rooms in the cinema.
     *
     * @return The list of rooms.
     *
     */
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from rooms");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setSeats(rs.getInt("seats"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * Get the movie of id.
     *
     * @param id The id of movie.
     *
     * @return The movie of given id if exists otherwise null.
     *
     */
    public Movie getMovie(int id) {
        Movie movie = new Movie();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from movies where id=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                movie.setId(rs.getInt("id"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setDescription(rs.getString("description"));
                movie.setPoster(rs.getString("poster"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setLength(rs.getInt("length"));
                movie.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    /**
     * Search movies by a key and the gender.
     *
     * @param key The key to search for.
     * @param gender The gender to search.
     *
     * @return The user of given username and password if exists otherwise null.
     *
     */
    public List<Movie> getMovies(String key, String gender) {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from movies where title like ? and gender like ?");
            stm.setString(1, "%" + key + "%");
            stm.setString(2, "%" + gender + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setDescription(rs.getString("description"));
                movie.setPoster(rs.getString("poster"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setLength(rs.getInt("length"));
                movie.setGender(rs.getString("gender"));
                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * List of programed projections of a movie.
     *
     * @param movie The movie.
     *
     * @return The projections list of the movie.
     *
     */
    public List<Projection> getProjections(Movie movie) {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        List<Projection> projections = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select p.*, (select count(*) from bookings where projection_id=p.id) as booked  from projections as p where date>=? and movie_id = ?");
            stm.setString(1, format.format(new Date()));
            stm.setString(2, movie.getId() + "");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Projection projection = new Projection();
                projection.setId(rs.getInt("id"));
                projection.setBooked(rs.getInt("booked"));
                projection.setMovieId(rs.getInt("movie_id"));
                projection.setRoomId(rs.getInt("room_id"));
                projection.setMovie(getMovie(rs.getInt("movie_id")));
                projection.setRoom(getRoom(rs.getInt("room_id")));
                projection.setDate(new java.util.Date(rs.getTimestamp("date").getTime()));
                projection.setPrice(rs.getInt("price"));
                projections.add(projection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projections;
    }

    /**
     * Get all projections.
     *
     * @return The list of all projections.
     *
     */
    public List<Projection> getProjections() {
        //SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        List<Projection> projections = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select p.*, (select count(*) from bookings where projection_id=p.id) as booked  from projections as p");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Projection projection = new Projection();
                projection.setId(rs.getInt("id"));
                projection.setBooked(rs.getInt("booked"));
                projection.setMovieId(rs.getInt("movie_id"));
                projection.setRoomId(rs.getInt("room_id"));
                projection.setMovie(getMovie(rs.getInt("movie_id")));
                projection.setRoom(getRoom(rs.getInt("room_id")));
                projection.setDate(new java.util.Date(rs.getTimestamp("date").getTime()));
                projection.setPrice(rs.getInt("price"));
                projections.add(projection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projections;
    }

    /**
     * Get projection by id.
     *
     * @param id The id of the projection to look for.
     *
     * @return The projection of the given id if exists otherwise null.
     *
     */
    public Projection getProjection(int id) {
        Projection projection = new Projection();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from projections as p where id=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                projection.setId(rs.getInt("id"));
                projection.setMovieId(rs.getInt("movie_id"));
                projection.setRoomId(rs.getInt("room_id"));
                projection.setDate(new java.util.Date(rs.getTimestamp("date").getTime()));
                projection.setPrice(rs.getInt("price"));
                return projection;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the list of the booking of a projection.
     *
     * @param projection The projection to look projection for.
     *
     * @return The list of booking of the given projection.
     *
     */
    public List<Booking> getBookings(Projection projection) {
        List<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from bookings where projection_id = ?");
            stm.setInt(1, projection.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setProjectionId(rs.getInt("projection_id"));
                booking.setSeat(rs.getInt("seat"));
                booking.setPrice(rs.getInt("price"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Get list of the booking of given user.
     *
     * @param user The user.
     *
     * @return The list of the bookings of a given user.
     *
     */
    public List<Booking> getBookings(User user) {
        List<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from bookings where user_id = ?");
            stm.setInt(1, user.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setProjectionId(rs.getInt("projection_id"));
                booking.setSeat(rs.getInt("seat"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Get list of all of the bookings.
     *
     * @return The list of all booking.
     *
     */
    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from bookings");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setProjectionId(rs.getInt("projection_id"));
                booking.setSeat(rs.getInt("seat"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Save a user in database.
     *
     * @param user The user object to save.
     *
     * @return The new saved user object.
     *
     */
    public User saveUser(User user) {
        try {
            PreparedStatement stm = connection.prepareStatement("insert into users(first_name, last_name, email, username, password, type) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getUsername());
            stm.setString(5, user.getPassword());
            stm.setString(6, user.getType());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try ( ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a booking in database.
     *
     * @param booking The booking to save.
     *
     * @return The new saved booking object.
     *
     */
    public Booking saveBooking(Booking booking) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("insert into bookings(user_id, projection_id, seat, transaction, price) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, booking.getUserId());
            stm.setInt(2, booking.getProjectionId());
            stm.setInt(3, booking.getSeat());
            stm.setString(4, booking.getTransaction());
            stm.setInt(5, booking.getPrice());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try ( ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getInt(1));
                    return booking;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get promocode object of the given code.
     *
     * @param code The code of promocode.
     *
     * @return The PromoCode .
     *
     */
    public PromoCode getPromoCode(String code) {
        PromoCode promo = new PromoCode();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from promos as p where code=?");
            stm.setString(1, code);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                promo.setId(rs.getInt("id"));
                promo.setCode(rs.getString("code"));
                promo.setDiscount(rs.getInt("discount"));
                return promo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Delete a movie from databse.
     *
     * @param movie The movie to remove.
     *
     * @return True if movie deleted succefully or False.
     *
     */
    public boolean delete(Movie movie) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("delete from movies where id=?");
            stm.setInt(1, movie.getId());
            int affectedRows = stm.executeUpdate();
            if (affectedRows != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Add new movie to databse.
     *
     * @param movie The movie to remove.
     *
     * @return True if movie deleted succefully or False.
     *
     */
    public Movie addMovie(Movie movie) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("insert into movies(title, year, poster, trailer, length, gender, description) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, movie.getTitle());
            stm.setInt(2, movie.getYear());
            stm.setString(3, movie.getPoster());
            stm.setString(4, movie.getTrailer());
            stm.setInt(5, movie.getLength());
            stm.setString(6, movie.getGender());
            stm.setString(7, movie.getDescription());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try ( ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                    return movie;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Update a movie in databse.
     *
     * @param movie The movie to update.
     *
     * @return True if movie updated succefully or False.
     *
     */
    public boolean updateMovie(Movie movie) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("update movies set title=?, year=? , poster=?, trailer=?, length=?, gender=?, description=? where id=?");
            stm.setString(1, movie.getTitle());
            stm.setInt(2, movie.getYear());
            stm.setString(3, movie.getPoster());
            stm.setString(4, movie.getTrailer());
            stm.setInt(5, movie.getLength());
            stm.setString(6, movie.getGender());
            stm.setString(7, movie.getDescription());
            stm.setInt(8, movie.getId());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Add new projection to databse.
     *
     * @param projection The projection to add.
     *
     * @return new projection object if projection added succefully or null.
     *
     */
    public Projection addProjection(Projection projection) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("insert into projections(movie_id, room_id, date, price) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, projection.getMovie().getId());
            stm.setInt(2, projection.getRoom().getId());
            stm.setTimestamp(3, new Timestamp(projection.getDate().getTime()));
            stm.setInt(4, projection.getPrice());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try ( ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projection.setId(generatedKeys.getInt(1));
                    return projection;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Update a projection in databse.
     *
     * @param projection The projection to update.
     *
     * @return True if projection updated succefully or False.
     *
     */
    public boolean updateProjection(Projection projection) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("update projections set movie_id=?, room_id=?, date=?,  price=? where id=?");
            stm.setInt(1, projection.getMovie().getId());
            stm.setInt(2, projection.getRoom().getId());
            stm.setTimestamp(3, new Timestamp(projection.getDate().getTime()));
            stm.setInt(4, projection.getPrice());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Delete a projection from databse.
     *
     * @param projection The movie to remove.
     *
     * @return True if projection deleted succefully or False.
     *
     */
    public boolean delete(Projection projection) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("delete from projections where id=?");
            stm.setInt(1, projection.getId());
            int affectedRows = stm.executeUpdate();
            if (affectedRows != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Add new room to databse.
     *
     * @param room The room to add.
     *
     * @return new room object if room added succefully or null.
     *
     */
    public Room addRoom(Room room) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("insert into rooms(name, seats) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, room.getName());
            stm.setInt(2, room.getSeats());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
            try ( ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getInt(1));
                    return room;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update a room in databse.
     *
     * @param room The room to update.
     *
     * @return True if room updated succefully or False.
     *
     */
    public boolean updateRoom(Room room) {
        try {
            PreparedStatement stm;
            stm = connection.prepareStatement("update rooms set name=?, seats=? where id=?");
            stm.setString(1, room.getName());
            stm.setInt(2, room.getSeats());
            stm.setInt(3, room.getId());
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
