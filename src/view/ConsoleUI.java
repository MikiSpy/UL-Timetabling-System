package view;

import java.util.Scanner;
import controller.*;
import model.*;
import repository.CSVTimetableRepository;
import repository.TimetableRepository;
import view.*;

/**
 * Provides a text-based interface for the system.
 */
public class ConsoleUI {

    private static final UserController userController = new UserController();

    private static TimetableRepository repo = new CSVTimetableRepository("data/sessionTimetable.csv");
    private static TimetableController timetableController = new TimetableController(repo);
    private static final AdminController adminController = new AdminController(repo);

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the text-based interface.
     */
    public static void showMenu() {
        while (true) {
            System.out.println("\n=== TIMETABLE SYSTEM MENU ===");
            System.out.println("1. Student Login");
            System.out.println("2. Lecturer Login");
            System.out.println("3. Admin Login");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    studentLogin();
                    break;
                case 2:
                    lecturerLogin();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void studentLogin() {
        System.out.println("\nEnter Student ID: ");
        String id = scanner.nextLine();

        System.out.println("\nEnter Student password: ");
        String password = scanner.nextLine();

        User user = userController.login(id, password);

        if (!(user instanceof Student)) {
            System.out.println("Invalid Student login.");
            return;
        }

        System.out.println("\nLogin successful! Welcome, " + user.getName());
        StudentMenu.showMenu(userController, (Student) user, timetableController);
    }

    private static void lecturerLogin() {
        System.out.println("\nEnter Lecturer ID: ");
        String id = scanner.nextLine();

        System.out.println("\nEnter Lecturer password: ");
        String password = scanner.nextLine();

        User lecturer = userController.login(id, password);

        if (!(lecturer instanceof Lecturer)) {
            System.out.println("Invalid Lecturer login.");
            return;
        }

        System.out.println("\nLogin successful! Welcome, " + lecturer.getName());
        LecturerMenu.showMenu(userController, (Lecturer) lecturer, timetableController);
    }

    private static void adminLogin() {
        System.out.println("\nEnter Admin ID: ");
        String id = scanner.nextLine();

        System.out.println("\nEnter Admin password: ");
        String password = scanner.nextLine();

        User admin = userController.login(id, password);

        if (!(admin instanceof Admin)) {
            System.out.println("Invalid Admin login.");
            return;
        }
        System.out.println("\nSuccessfully logged in as admin!");
        AdminMenu.showMenu(adminController, (Admin) admin);
    }

    private static int getIntInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
