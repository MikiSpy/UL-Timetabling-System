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

    private static final String USERS_FILE = "data/users.csv";
    private CSVUtil csvUtil = new CSVUtil();

    /**
     * Attempts to log a user in.
     * @param username username input
     * @param password password input
     * @return the authenticated user, or null if login fails
     */
    public User login(String username, String password) {
        String[][] rows = csvUtil.readCSV(USERS_FILE);

        for (int i = 1; i < rows.length; i++) {
            String[] row = rows[i];
            String id = row[0];
            String name = row[1];
            String email = row[2];
            String storedPassword = row[3];
            String role = row[4];

            if (name.equals(username) && storedPassword.equals(password)) {
                return createUser(id, name, email, storedPassword, role);
            }
        }
        return null;
    }

    private User createUser(String id, String name, String email, String password, String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return new Admin(name, password);
            case "LECTURER":
                return new Lecturer(name, password, id, email);
            case "STUDENT":
                return new Student(name, password, id, email);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
