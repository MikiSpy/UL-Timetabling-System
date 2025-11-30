package view;

import controller.*;
import model.*;
import model.Module;
import repository.CSVTimetableRepository;
import java.time.LocalTime;
import java.util.Scanner;

public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(AdminController adminController, Admin admin, TimetableController timetableController) {
        while (true) {
            System.out.println("\n=== ADMIN TIMETABLE MENU ===");
            System.out.println("1. View Programme Schedule");
            System.out.println("2. View Module Schedule");
            System.out.println("3. Edit a Timetable Slot");
            System.out.println("4. Delete a Timetable Slot");
            System.out.println("5. Add a Timetable Slot");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {

                case 1:
                    System.out.print("Enter the programme code: ");
                    String programmeCode = scanner.nextLine().trim();
                    Timetable programmeTimetable = timetableController.getTimetableByProgrammeCode(programmeCode);
                    viewTimetable(programmeTimetable);
                    break;

                case 2:
                    System.out.print("Enter module code: ");
                    String moduleCode = scanner.nextLine().trim();
                    Timetable moduleTimetable = timetableController.getTimetableByModule(moduleCode);
                    viewTimetable(moduleTimetable);
                    break;

                case 3:
                    editTimetableSlot(timetableController);
                    break;

                case 4:
                    deleteTimetableSlot(timetableController);
                    break;

                case 5:
                    createTimetableSlot(timetableController);
                    break;

                case 0:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // -----------------------------------------
    // VIEW TIMETABLE (same style as Lecturer/Student)
    // -----------------------------------------
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
                    slot.getStudentGroup() != null ? slot.getStudentGroup().getId() : "TBD",
                    slot.getLecturer() != null ? slot.getLecturer().getName() : "TBD",
                    slot.getType() != null ? slot.getType() : "TBD"
            );
        }
    }

    // -----------------------------------------
    // EDITING FEATURE
    // -----------------------------------------
    private static void editTimetableSlot(TimetableController timetableController) {

        System.out.print("Enter module code to load slots for editing: ");
        String moduleCode = scanner.nextLine().trim();

        Timetable timetable = timetableController.getTimetableByModule(moduleCode);

        if (timetable == null || timetable.getSlots().isEmpty()) {
            System.out.println("No slots found for that module.");
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

        if (success) {
            System.out.println("Slot updated successfully.");
        } else {
            System.out.println("Failed to update slot. Check your input.");
        }
    }

    private static void deleteTimetableSlot(TimetableController timetableController) {
        System.out.print("Enter module code to load slots: ");
        String moduleCode = scanner.nextLine().trim();

        Timetable timetable = timetableController.getTimetableByModule(moduleCode);
        if (timetable == null || timetable.getSlots().isEmpty()) {
            System.out.println("No slots found for that module.");
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

        if (success) {
            System.out.println("Slot deleted successfully!");
        } else {
            System.out.println("Failed to delete slot.");
        }
    }

    private static void createTimetableSlot(TimetableController timetableController) {

        System.out.print("Enter module code: ");
        String moduleCode = scanner.nextLine().trim();

        Timetable timetable = timetableController.getTimetableByModule(moduleCode);
        if (timetable == null) {
            System.out.println("Invalid module code.");
            return;
        }

        System.out.println("\n--- Create Timetable Slot ---");

        System.out.print("Day (e.g. Monday): ");
        String day = scanner.nextLine().trim();

        System.out.print("Start Time (HH:MM): ");
        LocalTime start = LocalTime.parse(scanner.nextLine().trim());

        System.out.print("End Time (HH:MM): ");
        LocalTime end = LocalTime.parse(scanner.nextLine().trim());

        System.out.print("Semester (e.g. 1): ");
        int semester = getIntInput();

        System.out.print("Weeks: ");
        String weeks = scanner.nextLine().trim();


        System.out.print("Room Number: ");
        String roomNumber = scanner.nextLine().trim();

        System.out.print("Room Type: ");
        String roomType = scanner.nextLine().trim();

        System.out.print("Room Capacity: ");
        int roomCap = getIntInput();
        Room room = new Room(roomType, roomNumber, roomCap);

        System.out.print("Lecturer ID: ");
        String lecturerId = scanner.nextLine().trim();
        Lecturer lecturer = new Lecturer("TBD", "tbd", lecturerId, "tbd@ul.ie");

        System.out.print("Student Group ID: ");
        String studentGroupId = scanner.nextLine().trim();

        System.out.print("Student Subgroup ID: ");
        String subId = scanner.nextLine().trim();
        Subgroup subgroup = new Subgroup(subId);

        Module module = timetableController.getModule(moduleCode);

        System.out.print("Session Type (LECTURE/LAB/TUTORIAL): ");
        SessionType type = SessionType.valueOf(scanner.nextLine().trim().toUpperCase());

        TimetableSlot slot = new TimetableSlot(day, start, end, semester, weeks, room, lecturer,
                studentGroupId, subgroup, module, type);

        boolean ok = timetableController.addTimetableSlot(slot);

        if (ok) System.out.println("Slot added successfully!");
        else System.out.println("Error adding slot.");
    }


    // -----------------------------------------
    // SAFE INT INPUT
    // -----------------------------------------
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
