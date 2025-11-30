package controller;

import model.Admin;
import model.Lecturer;
import model.Student;
import model.User;
import util.CSVUtil;

/**
 * Handles user login and authentication.
 */
public class UserController {

    private final CSVUtil csvUtil = new CSVUtil();
    private static final String STUDENTS_FILE = "data/students.csv";
    private static final String LECTURERS_FILE = "data/lecturers.csv";
    private static final String ADMINS_FILE = "data/admins.csv";

    /**
     * Attempts to log a user in.
     * @param idInput id input
     * @param password password input
     * @return the authenticated user, or null if login fails
     */
    public User login(String idInput, String password) {
        // Try students
        User user = loginFromCSV(STUDENTS_FILE, idInput, password, "STUDENT");
        if (user != null) return user;

        // Try lecturers
        user = loginFromCSV(LECTURERS_FILE, idInput, password, "LECTURER");
        if (user != null) return user;

        // Try admins
        return loginFromCSV(ADMINS_FILE, idInput, password, "ADMIN");
    }


    private User loginFromCSV(String file, String idInput, String password, String role) {
        String[][] rows = csvUtil.readCSV(file);

        for (int i = 1; i < rows.length; i++) { // skip header
            String[] row = rows[i];

            if (row.length < 4) {
                // skip blank or malformed rows
                continue;
            }

            String id = row[0];
            String name = row[1];
            String email = row[2];
            String storedPassword = row[3];

            String studentGroupId = null;
            if ("STUDENT".equalsIgnoreCase(role) && row.length > 4) {
                studentGroupId = row[4];
            }

            if (id.equals(idInput) && storedPassword.equals(password)) {
                return createUser(id, name, email, storedPassword, role, studentGroupId);
            }
        }

        return null;
    }



    private User createUser(String id, String name, String email, String password, String role, String studentGroupId) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return new Admin(name, password);
            case "LECTURER":
                return new Lecturer(id, name, email, password);
            case "STUDENT":
                // assign studentGroupId here
                return new Student(name, password, id, null, 1, studentGroupId);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
