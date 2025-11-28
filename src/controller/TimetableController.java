package controller;
import model.User;
import model.Student;
import model.Lecturer;
import model.Admin;
import model.Timetable;

/**
 * Handles operations related to timetable queries.
 */
public class TimetableController {

    /**
     * Returns the timetable for a given user.
     * @param user the user
     * @return the timetable
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

    private Timetable getTimetableForStudent(Student student) {
        return new Timetable();
    }

    private Timetable getTimetableForLecturer(Lecturer lecturer) {
        return new Timetable();
    }

    private Timetable getTimetableForAdmin(Admin admin) {
        return new Timetable();
    }
}
