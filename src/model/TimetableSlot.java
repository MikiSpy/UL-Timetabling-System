package model;

/**
 * Represents a single scheduled session (lecture, lab, or tutorial).
 */
public class TimetableSlot {

    /**
     * Returns the day of the week for this slot.
     * @return day index
     */
    public int getDay() {
        return 0;
    }

    /**
     * Returns the start time of the session.
     * @return start time as a string
     */
    public String getStartTime() {
        return "";
    }

    /**
     * Returns the room used for this session.
     * @return the room
     */
    public Room getRoom() {
        return null;
    }
}
