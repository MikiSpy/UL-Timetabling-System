package model;

/**
 * Represents a room in the university.
 */
public class Room {
    private String type;
    private String number;
    private int capacity;

    /**
     * Create a room.
     * @param number room number
     * @param type room type
     * @param capacity room capacity
     */
    public Room(String number, String type, int capacity) {
        this.type = type;
        this.number = number;
        this.capacity = capacity;
    }

    /**
     * Get room capacity.
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get room number.
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Get room type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set room number.
     * @param number new number
     */
    public void setNumber(String number) {
        this.number = number;
    }
}
