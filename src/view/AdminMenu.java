package view;

import controller.AdminController;
import controller.UserController;
import model.Admin;
import java.util.Scanner;

public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(AdminController adminController, Admin admin) {
        while (true) {
            System.out.println("\n=== TIMETABLE SYSTEM MENU ===");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

          /*  int choice = getIntInput();

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
           */
        }
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
