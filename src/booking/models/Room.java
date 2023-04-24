/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking.models;

/**
 * A class that represents a room in the cinema.
 *
 */
public class Room {
    
    int id;
    String name;
    int seats;

    /**
     * Default constructor
     */
    public Room() {
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
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getSeats() {
        return seats;
    }

    /**
     *
     * @param seats
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
    
}
