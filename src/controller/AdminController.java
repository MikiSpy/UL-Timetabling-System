package controller;

import model.*;
import repository.*;
import java.time.LocalTime;

/**
 * Controller for admin actions like managing timetables, modules, and rooms.
 */
public class AdminController {
    private final TimetableRepository timetableRepo;
    private final ModuleRepository moduleRepo;
    private final RoomRepository roomRepo;
    private final LecturerRepository lecturerRepo;

    /**
     * Create an AdminController with the given repositories.
     * @param timetableRepo timetable repo
     * @param moduleRepo module repo
     * @param roomRepo room repo
     * @param lecturerRepo lecturer repo
     */
    public AdminController(TimetableRepository timetableRepo, ModuleRepository moduleRepo,
                           RoomRepository roomRepo, LecturerRepository lecturerRepo) {
        this.timetableRepo = timetableRepo;
        this.moduleRepo = moduleRepo;
        this.roomRepo = roomRepo;
        this.lecturerRepo = lecturerRepo;
    }

    /**
     * Create a new timetable slot.
     * @param day day of session
     * @param startTime start time
     * @param endTime end time
     * @param semester semester number
     * @param weeks weeks pattern
     * @param roomNumber room number
     * @param lecturerId lecturer ID
     * @param studentGroup student group
     * @param subgroup subgroup
     * @param module module object
     * @param type session type
     * @return true if saved
     */
    public boolean createTimetableSlot(String day, LocalTime startTime, LocalTime endTime,
                                       int semester, String weeks, String roomNumber,
                                       String lecturerId, StudentGroup studentGroup,
                                       Subgroup subgroup, model.Module module, SessionType type) {

        Room room = roomRepo.findByNumber(roomNumber);
        Lecturer lecturer = lecturerRepo.findById(lecturerId);
        TimetableSlot slot = new TimetableSlot(day, startTime, endTime, semester, weeks,
                room, lecturer, studentGroup.getId(), subgroup, module, type);

        Timetable timetable = timetableRepo.findAll();

        for (TimetableSlot existing : timetable.getSlots()) {
            if (slot.conflictsWith(existing)) {
                throw new IllegalArgumentException("Conflict detected with existing slot");
            }
        }
        return timetableRepo.saveSlot(slot);
    }

    /**
     * Edit a timetable slot.
     * @param slot slot to edit
     * @param fieldChoice field number
     * @param newValue new value
     * @return true if updated
     */
    public boolean editTimetableSlot(TimetableSlot slot, int fieldChoice, String newValue) {
        return timetableRepo.updateSlot(slot, fieldChoice, newValue);
    }

    /**
     * Delete a timetable slot.
     * @param slot slot to delete
     * @return true if deleted
     */
    public boolean deleteTimetableSlot(TimetableSlot slot) {
        return timetableRepo.deleteSlot(slot);
    }

    /**
     * Create a new module.
     * @param code module code
     * @param title module title
     * @param lectureHours lecture hours
     * @param labHours lab hours
     * @param tutorialHours tutorial hours
     * @param year year of study
     * @param semester semester number
     * @param weeks weeks pattern
     * @param programmeCode programme code
     * @param lecturerId lecturer ID
     * @return true if saved
     */
    public boolean createModule(String code, String title, int lectureHours, int labHours,
                                int tutorialHours, int year, int semester, String weeks,
                                String programmeCode, String lecturerId) {
        model.Module module = new model.Module(code, title, lectureHours, labHours, tutorialHours,
                year, semester, weeks, programmeCode);
        return moduleRepo.save(module);
    }

    /**
     * Delete a module.
     * @param code module code
     * @return true if deleted
     */
    public boolean deleteModule(String code) {
        return moduleRepo.delete(code);
    }

    /**
     * Create a new room.
     * @param number room number
     * @param type room type
     * @param capacity room capacity
     * @return true if saved
     */
    public boolean createRoom(String number, String type, int capacity) {
        Room room = new Room(number, type, capacity);
        return roomRepo.save(room);
    }

    /**
     * Delete a room.
     * @param number room number
     * @return true if deleted
     */
    public boolean deleteRoom(String number) {
        return roomRepo.delete(number);
    }

    /**
     * Find a module by code.
     * @param code module code
     * @return module or null
     */
    public model.Module getModule(String code) {
        return moduleRepo.findByCode(code);
    }
}
