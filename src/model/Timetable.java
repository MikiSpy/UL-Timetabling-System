package model;
import java.util.ArrayList;

/**
 * Represents a timetable for a student, lecturer, or room.
 */
public class Timetable {
    private ArrayList<TimetableSlot> slots = new ArrayList<>();

    Timetable(ArrayList<TimetableSlot> slots) {
        this.slots = slots;
    }
    /**
     * Adds a slot to the timetable.
     * @param slot the timetable slot
     */
    public void addSlot(TimetableSlot slot) {
        slots.add(slot);
    }

    /**
     * Returns all slots in the timetable.
     * @return array of timetable slots
     */
    public ArrayList<TimetableSlot> getSlots() {
        return slots;
    }
}
