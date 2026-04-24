import java.util.Scanner;

/**
 * Main entry point for the Dog Food pantry management application.
 * Handles the primary console menu loop and routes user input to
 * the appropriate feature handlers.
 *
 * Author: Zachary Amith
 */

public class Main {

    // Shared Scanner instance so we don't open/close System.in repeatedly
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Dog Food - Pantry Manager");

        // Main event loop: display menu, read choice, dispatch
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": System.out.println("[Not yet implemented]"); break;
                case "2": System.out.println("[Not yet implemented]"); break;
                case "3": System.out.println("[Not yet implemented]"); break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    /**
     * Displays the top-level menu options to the user.
     * Options are numbered for easy keyboard selection.
     */
    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Ingredient");
        System.out.println("2. View Pantry");
        System.out.println("3. Remove Ingredient");
        System.out.println("0. Quit");
        System.out.print("Choice: ");
    }
}