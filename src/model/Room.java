package model;

/**
 * Represents a generic room in the university.
 */
public class Room {
    private String type, number;
    private int capacity;

    public Room(String number, String type, int capacity) {
        this.type = type;
        this.number = number;
        this.capacity = capacity;
    }

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

    public String getType() {
        return type;
    }

    public void setNumber(String number) {}
}
