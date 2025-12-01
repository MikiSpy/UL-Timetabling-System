package controller;

import model.*;
import repository.*;

/**
 * Handles timetable operations for students, lecturers, and admins.
 */
public class TimetableController {

    private TimetableRepository repo;

    /**
     * Create a controller with the given repository.
     * @param repo timetable repository
     */
    public TimetableController(TimetableRepository repo) {
        this.repo = repo;
    }

    /**
     * Get a timetable for a user.
     * @param user student, lecturer or admin
     * @return timetable for that user
     */
    public Timetable getTimetableForUser(User user) {
        if (user instanceof Student) {
            return getTimetableForStudent((Student) user);
        } else if (user instanceof Lecturer) {
            return getTimetableForLecturer((Lecturer) user);
        } else if (user instanceof Admin) {
            return getTimetableForAdmin((Admin) user);
        } else {
            throw new IllegalArgumentException("Unknown user type: " + user.getClass().getName());
        }
    }

    /**
     * Get timetable slots for a student’s group.
     * @param student student object
     * @return timetable with matching slots
     */
    private Timetable getTimetableForStudent(Student student) {
        Timetable allSlots = repo.findAll();
        Timetable studentTimetable = new Timetable();

        String studentGroupId = student.getStudentGroupId();

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null) continue;
            if (studentGroupId != null && studentGroupId.equals(slot.getStudentGroupId())) {
                studentTimetable.addSlot(slot);
            }
        }
        return studentTimetable;
    }

    /**
     * Get timetable slots taught by a lecturer.
     * @param lecturer lecturer object
     * @return timetable with matching slots
     */
    private Timetable getTimetableForLecturer(Lecturer lecturer) {
        Timetable allSlots = repo.findAll();
        Timetable lecturerTimetable = new Timetable();

        String lecturerId = lecturer.getId();

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null || slot.getLecturer() == null) continue;
            if (slot.getLecturer().getId().equals(lecturerId)) {
                lecturerTimetable.addSlot(slot);
            }
        }
        return lecturerTimetable;
    }

    /**
     * Filter timetable by programme code.
     * @param programmeCode code of programme
     * @return timetable with matching slots
     */
    public Timetable getTimetableByProgrammeCode(String programmeCode) {
        Timetable allSlots = repo.findAll();
        Timetable filtered = new Timetable();

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null || slot.getModule() == null) continue;
            if (slot.getModule().getProgrammeCode() != null &&
                    slot.getModule().getProgrammeCode().equalsIgnoreCase(programmeCode)) {
                filtered.addSlot(slot);
            }
        }
        return filtered;
    }

    /**
     * Filter timetable by module code.
     * @param moduleCode code of module
     * @return timetable with matching slots
     */
    public Timetable getTimetableByModule(String moduleCode) {
        Timetable allSlots = repo.findAll();
        Timetable filtered = new Timetable();

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null || slot.getModule() == null) continue;
            if (slot.getModule().getCode().equalsIgnoreCase(moduleCode)) {
                filtered.addSlot(slot);
            }
        }
        return filtered;
    }

    /**
     * Update a field in a timetable slot.
     * @param slot timetable slot
     * @param fieldChoice which field to change
     * @param newValue new value
     * @return true if updated
     */
    public boolean updateSlotField(TimetableSlot slot, int fieldChoice, String newValue) {
        try {
            switch (fieldChoice) {
                case 1 -> slot.setDay(newValue);
                case 2 -> slot.setStartTime(java.time.LocalTime.parse(newValue));
                case 3 -> slot.setEndTime(java.time.LocalTime.parse(newValue));
                case 4 -> slot.getRoom().setNumber(newValue);
                case 5 -> slot.getLecturer().setId(newValue);
                case 6 -> slot.setStudentGroupId(newValue);
                case 7 -> slot.setType(SessionType.valueOf(newValue.toUpperCase()));
                default -> { return false; }
            }
            return repo.overwriteAllSlots(repo.findAll());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Admins don’t have a personal timetable.
     * @param admin admin object
     * @return empty timetable
     */
    private Timetable getTimetableForAdmin(Admin admin) {
        return new Timetable();
    }

    /**
     * Delete a timetable slot.
     * @param slot timetable slot
     * @return true if deleted
     */
    public boolean deleteSlot(TimetableSlot slot) {
        return repo.deleteSlot(slot);
    }

    /**
     * Add a new timetable slot.
     * @param slot timetable slot
     * @return true if saved
     */
    public boolean addTimetableSlot(TimetableSlot slot) {
        return repo.saveSlot(slot);
    }

    /**
     * Find a module by code.
     * @param moduleCode code of module
     * @return module or null
     */
    public model.Module getModule(String moduleCode) {
        Timetable allSlots = repo.findAll();
        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot.getModule().getCode().equalsIgnoreCase(moduleCode)) {
                return slot.getModule();
            }
        }
        return null;
    }
}
