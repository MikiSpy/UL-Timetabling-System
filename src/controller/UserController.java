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
     * Try to log a user in by checking student, lecturer, and admin CSV files.
     * @param idInput user ID
     * @param password user password
     * @return authenticated user or null
     */
    public User login(String idInput, String password) {
        User user = loginFromCSV(STUDENTS_FILE, idInput, password, "STUDENT");
        if (user != null) return user;

        user = loginFromCSV(LECTURERS_FILE, idInput, password, "LECTURER");
        if (user != null) return user;

        return loginFromCSV(ADMINS_FILE, idInput, password, "ADMIN");
    }

    /**
     * Check a specific CSV file for a matching user.
     * @param file CSV file path
     * @param idInput user ID
     * @param password user password
     * @param role role type (STUDENT, LECTURER, ADMIN)
     * @return authenticated user or null
     */
    private User loginFromCSV(String file, String idInput, String password, String role) {
        String[][] rows = csvUtil.readCSV(file);

        for (int i = 1; i < rows.length; i++) { // skip header
            String[] row = rows[i];

            if (row.length < 4) continue;

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

    /**
     * Create a user object based on role.
     * @param id user ID
     * @param name user name
     * @param email user email
     * @param password user password
     * @param role role type
     * @param studentGroupId student group ID (students only)
     * @return user object
     */
    private User createUser(String id, String name, String email, String password, String role, String studentGroupId) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return new Admin(name, password);
            case "LECTURER":
                return new Lecturer(id, name, email, password);
            case "STUDENT":
                return new Student(name, password, id, null, 1, studentGroupId);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
