package model;

/**
 * Represents a generic room in the university.
 */
public abstract class Room {

    /**
     * Returns the capacity of the room.
     * @return room capacity
     */
    public int getCapacity() {
        return 0;
    }

    /**
     * Returns the room name or number.
     * @return room name
     */
    public String getName() {
        return "";
    }
}
