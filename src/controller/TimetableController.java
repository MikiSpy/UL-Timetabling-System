package controller;
import model.*;
import model.Module;
import repository.*;

/**
 * Handles operations related to timetable queries.
 */
public class TimetableController {

    private TimetableRepository repo;

    public TimetableController(TimetableRepository repo) {
        this.repo = repo;
    }

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

    private Timetable getTimetableForStudent(Student student) {
        Timetable allSlots = repo.findAll(); // load all slots from CSV
        Timetable studentTimetable = new Timetable();

        // get the student's group ID
        String studentGroupId = student.getStudentGroupId();
        System.out.println("Student group ID: " + studentGroupId);

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null) continue; // skip null slots

            // get the slot's student group ID directly
            String slotGroupId = slot.getStudentGroupId(); // <--- use this
            System.out.println("Slot group ID: " + slotGroupId);

            // compare IDs
            if (studentGroupId != null && studentGroupId.equals(slotGroupId)) {
                studentTimetable.addSlot(slot);
            }
        }

        if (studentTimetable.getSlots().isEmpty()) {
            System.out.println("No matching timetable slots found for student group: " + studentGroupId);
        }

        return studentTimetable;
    }



    private Timetable getTimetableForLecturer(Lecturer lecturer) {
        Timetable allSlots = repo.findAll(); // load all slots from CSV
        Timetable lecturerTimetable = new Timetable();

        String lecturerId = lecturer.getId(); // or getLecturerId() if your Lecturer class has that

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null || slot.getLecturer() == null) continue;

            if (slot.getLecturer().getId().equals(lecturerId)) {
                lecturerTimetable.addSlot(slot);
            }
        }

        if (lecturerTimetable.getSlots().isEmpty()) {
            System.out.println("No matching timetable slots found for lecturer: " + lecturer.getName());
        }

        return lecturerTimetable;
    }

    public Timetable getTimetableByProgrammeCode(String programmeCode) {
        Timetable allSlots = repo.findAll();
        Timetable filtered = new Timetable();

        for (TimetableSlot slot : allSlots.getSlots()) {
            if (slot == null || slot.getModule() == null) continue;

            // Compare programme code (3rd column in CSV)
            if (slot.getModule().getProgrammeCode() != null &&
                    slot.getModule().getProgrammeCode().equalsIgnoreCase(programmeCode)) {
                filtered.addSlot(slot);
            }
        }
        return filtered;
    }



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

    public boolean updateSlotField(TimetableSlot slot, int fieldChoice, String newValue) {
        try {
            switch (fieldChoice) {

                case 1: // Day
                    slot.setDay(newValue);
                    break;

                case 2: // Start Time
                    slot.setStartTime(java.time.LocalTime.parse(newValue));
                    break;

                case 3: // End Time
                    slot.setEndTime(java.time.LocalTime.parse(newValue));
                    break;

                case 4: // Room
                    slot.getRoom().setNumber(newValue);
                    break;

                case 5: // Lecturer
                    slot.getLecturer().setId(newValue);
                    break;

                case 6: // Student Group
                    slot.setStudentGroupId(newValue);
                    break;

                case 7: // Type
                    slot.setType(SessionType.valueOf(newValue.toUpperCase()));
                    break;

                default:
                    System.out.println("Unknown field choice.");
                    return false;
            }

            // REWRITE THE CSV
            return repo.overwriteAllSlots(repo.findAll());

        } catch (Exception e) {
            System.out.println("Error updating slot: " + e.getMessage());
            return false;
        }
    }



    private Timetable getTimetableForAdmin(Admin admin) {
        return new Timetable();
    }

    public boolean deleteSlot(TimetableSlot slot){
        return repo.deleteSlot(slot);
    }

    public boolean addTimetableSlot(TimetableSlot slot){
        return repo.saveSlot(slot);
    }

    public Module getModule(String moduleCode){
        Timetable allSlots = repo.findAll();

        for(TimetableSlot slot : allSlots.getSlots()){
            if(slot.getModule().getCode().equalsIgnoreCase(moduleCode)){
                return slot.getModule();
            }
        }
        return null;
    }
}
