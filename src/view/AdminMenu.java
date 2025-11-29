package view;

import controller.*;
import model.*;

import java.util.Scanner;

public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(AdminController adminController, Admin admin, TimetableController timetableController) {
        while (true) {
            System.out.println("\n=== ADMIN TIMETABLE MENU ===");
            System.out.println("1. View Programme Schedule");
            System.out.println("2. View Module Schedule");
            System.out.println("3. Edit a Timetable Slot");
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
