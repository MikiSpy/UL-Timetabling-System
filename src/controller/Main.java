package controller;

import view.ConsoleUI;

/**
 * Entry point for the Timetable Management System.
 */
public class Main {

    /**
     * Start the application.
     */
    public static void main(String[] args) {
        ConsoleUI console = new ConsoleUI();
        console.showMenu();
    }
}
