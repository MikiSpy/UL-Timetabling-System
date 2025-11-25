package model;

/**
 * Represents a generic room in the university.
 */
public abstract class Room {
    private String type, number;
    private int capacity;

    /**
     * Returns the capacity of the room.
     * @return room capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the room number.
     * @return room name
     */
    public String getNumber() {
        return number;
    }
}
