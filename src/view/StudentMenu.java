package view;

import controller.*;
import model.*;
import java.util.Scanner;

/**
 * Student menu for viewing timetables and schedules.
 */
public class StudentMenu {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Show student menu and handle input.
     * @param userController user controller
     * @param student student user
     * @param timetableController timetable controller
     */
    public static void showMenu(UserController userController, Student student, TimetableController timetableController) {
        while (true) {
            System.out.println("\n=== STUDENT TIMETABLE MENU ===");
            System.out.println("1. View Timetable");
            System.out.println("2. View Programme Schedule");
            System.out.println("3. View Module Schedule");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> viewTimetable(student, timetableController);
                case 2 -> {
                    System.out.print("Enter programme code: ");
                    String programmeCode = scanner.nextLine().trim();
                    Timetable programmeTimetable = timetableController.getTimetableByProgrammeCode(programmeCode);
                    viewTimetable(programmeTimetable);
                }
                case 3 -> {
                    System.out.print("Enter module code: ");
                    String moduleCode = scanner.nextLine().trim();
                    Timetable moduleTimetable = timetableController.getTimetableByModule(moduleCode);
                    viewTimetable(moduleTimetable);
                }
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * View timetable for a student.
     * @param student student user
     * @param timetableController timetable controller
     */
    public static void viewTimetable(Student student, TimetableController timetableController) {
        Timetable timetable = timetableController.getTimetableForUser(student);

        if (timetable.getSlots().isEmpty()) {
            System.out.println("No timetable slots found.");
            return;
        }

        System.out.println("\n=== STUDENT TIMETABLE ===");
        for (TimetableSlot slot : timetable.getSlots()) {
            System.out.printf("%s | %s - %s | Module: %s | Room: %s | Lecturer: %s | Type: %s%n",
                    slot.getDay(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getModule() != null ? slot.getModule().getTitle() : "TBD",
                    slot.getRoom() != null ? slot.getRoom().getNumber() : "TBD",
                    slot.getLecturer() != null ? slot.getLecturer().getName() : "TBD",
                    slot.getType() != null ? slot.getType() : "TBD"
            );
        }
    }

    /**
     * View a timetable directly.
     * @param timetable timetable object
     */
    public static void viewTimetable(Timetable timetable) {
        if (timetable.getSlots().isEmpty()) {
            System.out.println("No timetable slots found.");
            return;
        }

        System.out.println("\n=== TIMETABLE ===");
        for (TimetableSlot slot : timetable.getSlots()) {
            System.out.printf("%s | %s - %s | Module: %s | Room: %s | Lecturer: %s | Type: %s%n",
                    slot.getDay(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getModule() != null ? slot.getModule().getTitle() : "TBD",
                    slot.getRoom() != null ? slot.getRoom().getNumber() : "TBD",
                    slot.getLecturer() != null ? slot.getLecturer().getName() : "TBD",
                    slot.getType() != null ? slot.getType() : "TBD"
            );
        }
    }

    /**
     * Read integer input safely.
     * @return integer entered
     */
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
