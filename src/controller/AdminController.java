package controller;
import model.Module;
import repository.*;
import model.*;

import java.time.LocalTime;

/**
 * Controller for admin actions such as modifying timetables.
 */
public class AdminController {
    private final TimetableRepository timetableRepo;
    private final ModuleRepository moduleRepo;
    private final RoomRepository roomRepo;
    private final StudentGroupRepository groupRepo;
    private final LecturerRepository lecturerRepo;

    public AdminController(TimetableRepository timetableRepo, ModuleRepository moduleRepo, RoomRepository roomRepo, StudentGroupRepository groupRepo, LecturerRepository lecturerRepo) {
        this.timetableRepo = timetableRepo;
        this.moduleRepo = moduleRepo;
        this.roomRepo = roomRepo;
        this.groupRepo = groupRepo;
        this.lecturerRepo = lecturerRepo;
    }
    /**
     * Creates a new timetable slot.
     * @return success status
     */

    public boolean createTimetableSlot(String day, LocalTime startTime, LocalTime endTime,
                                       int semester, String weeks, String roomNumber,
                                       String lecturerId, StudentGroup studentGroup,
                                       Subgroup subgroup, Module module, SessionType type) {

        Room room = roomRepo.findByNumber(roomNumber);
        Lecturer lecturer = lecturerRepo.findById(lecturerId);
        TimetableSlot slot = new TimetableSlot(day, startTime, endTime, semester, weeks, room, lecturer, studentGroup.getId(), subgroup, module, type);

        Timetable timetable = timetableRepo.findAll();

        for (TimetableSlot existing : timetable.getSlots()) {
            if (slot.conflictsWith(existing)) {
                throw new IllegalArgumentException("Conflict detected with existing slot");
            }
        }

        return timetableRepo.saveSlot(slot);
    }

    public boolean editTimetableSlot(TimetableSlot slot, int fieldChoice, String newValue) {
        return timetableRepo.updateSlot(slot, fieldChoice, newValue);
    }

    public boolean deleteTimetableSlot(TimetableSlot slot) {
        return timetableRepo.deleteSlot(slot);
    }


    public boolean createModule(String code, String title, int lectureHours, int labHours, int tutorialHours, int year, int semester, String weeks, String programmeCode) {
        Module module = new Module(code, title, lectureHours, labHours, tutorialHours, year, semester, weeks, programmeCode);
        return moduleRepo.save(module);
    }

    public boolean deleteModule(String code) {
        return moduleRepo.delete(code);
    }

    public boolean createRoom(String number, String type, int capacity) {
        Room room = new Room(number, type, capacity);
        return roomRepo.save(room);
    }

    public boolean deleteRoom(String number) {
        return roomRepo.delete(number);
    }

    public boolean createStudentGroup(String id, int size) {
        StudentGroup group = new StudentGroup(id);
        return groupRepo.save(group);
    }

    public boolean deleteStudentGroup(String id) {
        return groupRepo.delete(id);
    }


}
