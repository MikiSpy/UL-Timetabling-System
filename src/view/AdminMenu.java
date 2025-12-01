package view;

import controller.*;
import model.*;
import repository.RoomRepository;

import java.time.LocalTime;
import java.util.Scanner;

/**
 * Admin menu for managing timetables, modules, and rooms.
 */
public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Show admin menu and handle input.
     * @param adminController admin controller
     * @param admin admin user
     * @param timetableController timetable controller
     */
    public static void showMenu(AdminController adminController, Admin admin, TimetableController timetableController) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View Programme Schedule");
            System.out.println("2. View Module Schedule");
            System.out.println("3. Edit a Timetable Slot");
            System.out.println("4. Delete a Timetable Slot");
            System.out.println("5. Create a Timetable Slot");
            System.out.println("6. Create Module");
            System.out.println("7. Delete Module");
            System.out.println("8. Create Room");
            System.out.println("9. Delete Room");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter programme code: ");
                    String programmeCode = scanner.nextLine().trim();
                    Timetable programmeTimetable = timetableController.getTimetableByProgrammeCode(programmeCode);
                    viewTimetable(programmeTimetable);
                }
                case 2 -> {
                    System.out.print("Enter module code: ");
                    String moduleCode = scanner.nextLine().trim();
                    Timetable moduleTimetable = timetableController.getTimetableByModule(moduleCode);
                    viewTimetable(moduleTimetable);
                }
                case 3 -> editTimetableSlot(timetableController);
                case 4 -> deleteTimetableSlot(timetableController);
                case 5 -> createTimetableSlot(adminController, timetableController);
                case 6 -> createModule(adminController);
                case 7 -> deleteModule(adminController);
                case 8 -> createRoom(adminController);
                case 9 -> deleteRoom(adminController);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Display a timetable.
     * @param timetable timetable object
     */
    public static void viewTimetable(Timetable timetable) {
        if (timetable == null || timetable.getSlots().isEmpty()) {
            System.out.println("No timetable slots found.");
            return;
        }

        System.out.println("\n=== TIMETABLE ===");
        int index = 1;
        for (TimetableSlot slot : timetable.getSlots()) {
            System.out.printf("%d) %s | %s - %s | Module: %s | Room: %s | Group: %s | Lecturer: %s | Type: %s%n",
                    index++,
                    slot.getDay(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getModule() != null ? slot.getModule().getCode() + " - " + slot.getModule().getTitle() : "TBD",
                    slot.getRoom() != null ? slot.getRoom().getNumber() : "TBD",
                    slot.getStudentGroupId(),
                    slot.getLecturer() != null ? slot.getLecturer().getName() : "TBD",
                    slot.getType() != null ? slot.getType() : "TBD"
            );
        }
    }

    /**
     * Edit a timetable slot.
     * @param timetableController timetable controller
     */
    private static void editTimetableSlot(TimetableController timetableController) {
        System.out.print("Enter module code: ");
        String moduleCode = scanner.nextLine().trim();
        Timetable timetable = timetableController.getTimetableByModule(moduleCode);

        if (timetable == null || timetable.getSlots().isEmpty()) {
            System.out.println("No slots found.");
            return;
        }

        viewTimetable(timetable);
        System.out.print("\nSelect slot number to edit: ");
        int slotIndex = getIntInput();

        if (slotIndex < 1 || slotIndex > timetable.getSlots().size()) {
            System.out.println("Invalid slot number.");
            return;
        }

        TimetableSlot slot = timetable.getSlots().get(slotIndex - 1);

        System.out.println("\nWhat would you like to edit?");
        System.out.println("1. Day");
        System.out.println("2. Start Time");
        System.out.println("3. End Time");
        System.out.println("4. Room");
        System.out.println("5. Lecturer");
        System.out.println("6. Student Group");
        System.out.println("7. Type");
        System.out.print("Select an option: ");

        int fieldChoice = getIntInput();
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine().trim();

        boolean success = timetableController.updateSlotField(slot, fieldChoice, newValue);
        System.out.println(success ? "Slot updated successfully." : "Failed to update slot.");
    }

    /**
     * Delete a timetable slot.
     * @param timetableController timetable controller
     */
    private static void deleteTimetableSlot(TimetableController timetableController) {
        System.out.print("Enter module code: ");
        String moduleCode = scanner.nextLine().trim();
        Timetable timetable = timetableController.getTimetableByModule(moduleCode);

        if (timetable == null || timetable.getSlots().isEmpty()) {
            System.out.println("No slots found.");
            return;
        }

        viewTimetable(timetable);
        System.out.print("\nSelect slot number to delete: ");
        int slotIndex = getIntInput();

        if (slotIndex < 1 || slotIndex > timetable.getSlots().size()) {
            System.out.println("Invalid slot number.");
            return;
        }

        TimetableSlot slot = timetable.getSlots().get(slotIndex - 1);
        boolean success = timetableController.deleteSlot(slot);
        System.out.println(success ? "Slot deleted successfully!" : "Failed to delete slot.");
    }

    /**
     * Create a new timetable slot.
     * @param adminController admin controller
     * @param timetableController timetable controller
     */
    private static void createTimetableSlot(AdminController adminController, TimetableController timetableController) {
        System.out.print("Enter module code: ");
        String moduleCode = scanner.nextLine().trim();
        model.Module module = adminController.getModule(moduleCode);

        if (module == null) {
            System.out.println("Module not found. Please create it first.");
            createModule(adminController);
            module = adminController.getModule(moduleCode);
            if (module == null) {
                System.out.println("Error: Module still not found.");
                return;
            }
        }

        System.out.println("\n--- Create Timetable Slot ---");
        System.out.print("Day: ");
        String day = scanner.nextLine().trim();
        System.out.print("Start Time (HH:MM): ");
        LocalTime start = LocalTime.parse(scanner.nextLine().trim());
        System.out.print("End Time (HH:MM): ");
        LocalTime end = LocalTime.parse(scanner.nextLine().trim());
        System.out.print("Semester: ");
        int semester = getIntInput();
        System.out.print("Weeks: ");
        String weeks = scanner.nextLine().trim();
        System.out.print("Room Number: ");
        String roomNumber = scanner.nextLine().trim();
        System.out.print("Lecturer ID: ");
        String lecturerId = scanner.nextLine().trim();
        System.out.print("Student Group ID: ");
        String studentGroupId = scanner.nextLine().trim();
        System.out.print("Subgroup ID: ");
        String subId = scanner.nextLine().trim();
        Subgroup subgroup = new Subgroup(subId);
        System.out.print("Session Type (LECTURE/LAB/TUTORIAL): ");
        SessionType type = SessionType.valueOf(scanner.nextLine().trim().toUpperCase());

        boolean ok = adminController.createTimetableSlot(day, start, end, semester, weeks, roomNumber, lecturerId,
                new StudentGroup(studentGroupId), subgroup, module, type);

        System.out.println(ok ? "Timetable slot created successfully!" : "Error creating slot.");
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

    /**
     * Create a new module.
     * @param adminController admin controller
     */
    private static void createModule(AdminController adminController) {
        System.out.print("Module Code: ");
        String code = scanner.nextLine().trim();

        System.out.print("Module Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Lecture Hours: ");
        int lectureHours = getIntInput();

        System.out.print("Lab Hours: ");
        int labHours = getIntInput();

        System.out.print("Tutorial Hours: ");
        int tutorialHours = getIntInput();

        System.out.print("Year (e.g. 1, 2, 3, 4): ");
        int year = getIntInput();

        System.out.print("Semester (e.g. 1 or 2): ");
        int semester = getIntInput();

        System.out.print("Weeks (e.g. 1-12): ");
        String weeks = scanner.nextLine().trim();

        System.out.print("Programme Code: ");
        String programmeCode = scanner.nextLine().trim();

        System.out.print("Lecturer ID: ");
        String lecturerId = scanner.nextLine().trim();

        boolean ok = adminController.createModule(
                code, title, lectureHours, labHours, tutorialHours,
                year, semester, weeks, programmeCode, lecturerId
        );

        System.out.println(ok ? "Module created successfully!" : "Error creating module.");
    }

    /**
     * Delete a module.
     * @param adminController admin controller
     */
    private static void deleteModule(AdminController adminController) {
        System.out.print("Enter Module Code to delete: ");
        String code = scanner.nextLine().trim();
        boolean ok = adminController.deleteModule(code);
        System.out.println(ok ? "Module deleted successfully!" : "Error deleting module.");
    }

    /**
     * Create a new room.
     * @param adminController admin controller
     */
    private static void createRoom(AdminController adminController) {
        System.out.print("Room Number: ");
        String number = scanner.nextLine().trim();

        System.out.print("Room Type: ");
        String type = scanner.nextLine().trim();

        System.out.print("Capacity: ");
        int capacity = getIntInput();

        boolean ok = adminController.createRoom(number, type, capacity);
        System.out.println(ok ? "Room created successfully!" : "Error creating room.");
    }

    /**
     * Delete a room.
     * @param adminController admin controller
     */
    private static void deleteRoom(AdminController adminController) {
        System.out.print("Enter Room Number to delete: ");
        String number = scanner.nextLine().trim();
        boolean ok = adminController.deleteRoom(number);
        System.out.println(ok ? "Room deleted successfully!" : "Error deleting room.");
    }
}
