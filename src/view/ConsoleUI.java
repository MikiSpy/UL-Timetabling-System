package view;

/**
 * Provides a text-based interface for the system.
 */
public class ConsoleUI {

    /**
     * Starts the text-based interface.
     */
    public void showMenu() {
        System.out.println("1. Login");
        System.out.println("2. View timetable");
        System.out.println("3. View programme/module/room schedule");
        System.out.println("4. Admin options");
        System.out.println("5. Exit");
    }
}
