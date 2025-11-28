package controller;
import model.Module;
import repository.*;
import model.*;

import java.time.LocalTime;

/**
 * Controller for admin actions such as modifying timetables.
 */
public class AdminController {
    private TimetableRepository repo;

    public AdminController(TimetableRepository repo) {
        this.repo = repo;
    }


    /**
     * Creates a new timetable slot.
     * @return success status
     */

    public boolean createTimetableSlot(String day, LocalTime startTime, LocalTime endTime,
                                       int semester, String weeks, Room room,
                                       Lecturer lecturer, StudentGroup studentGroup,
                                       Subgroup subgroup, Module module, SessionType type) {
        TimetableSlot slot = new TimetableSlot(day, startTime, endTime, semester, weeks, room, lecturer, studentGroup.getId(), subgroup, module, type);

        Timetable timetable = repo.findAll();

        for (TimetableSlot existing : timetable.getSlots()) {
            if (slot.conflictsWith(existing)) {
                throw new IllegalArgumentException("Conflict detected with existing slot");
            }
        }

        return repo.saveSlot(slot);
    }

    public boolean deleteTimetableSlot(int slotId){
        return false;
    }
}
