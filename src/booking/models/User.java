package booking.models;

import booking.DatabaseManager;

/**
 * A class represeting the user entity (employee and customer).
 *
 */
public class User {

    /**
     *
     */
    public static String TYPE_CUSTOMER = "CUSTOMER";

    /**
     *
     */
    public static String TYPE_EMPLOYE = "EMPLOYEE";

    private int id;
    private String firstName, lastName, username, password, email, type;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Constructor for the class User.
     *
     * @param id User id
     * @param firstName User first name
     * @param lastName User last name
     * @param username User username
     * @param password User password
     * @param email User email address
     * @param type User type
     */
    public User(int id, String firstName, String lastName, String username, String password, String email, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    /**
     * Returns the user's first name.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName New first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName New last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the user's full name.
     *
     * @return First and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the user's username.
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's password.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password New password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email New email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's type as a String.
     *
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the user's type.
     *
     * @param type New type
     */
    public void setType(String type) {
        this.type = type;
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
    public User save() {
        return DatabaseManager.getInstance().saveUser(this);
    }

}
