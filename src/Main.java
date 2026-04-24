import java.util.Scanner;

/**
 * Main entry point for the Dog Food pantry management application.
 * Handles the primary console menu loop and routes user input to
 * the appropriate feature handlers.
 *
 * Author: Zachary Amith
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Pantry pantry = new Pantry();

    public static void main(String[] args) {
        System.out.println("Welcome to Dog Food - Pantry Manager");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addIngredient(); break;
                case "2": viewPantry();    break;
                case "3": removeIngredient(); break;
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

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Ingredient");
        System.out.println("2. View Pantry");
        System.out.println("3. Remove Ingredient");
        System.out.println("0. Quit");
        System.out.print("Choice: ");
    }

    /**
     * Prompts the user for an ingredient's name, quantity, and unit,
     * then stores it in the pantry hash table. Handles invalid number
     * input without crashing the menu loop.
     */
    private static void addIngredient() {
        System.out.print("Ingredient name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        System.out.print("Quantity: ");
        String qtyStr = scanner.nextLine().trim();
        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity.");
            return;
        }

        System.out.print("Unit (e.g., cups, grams): ");
        String unit = scanner.nextLine().trim();
        if (unit.isEmpty()) { System.out.println("Unit cannot be empty."); return; }

        pantry.put(name, new Ingredient(name, qty, unit));
        System.out.println("Added: " + name);
    }

    /**
     * Prints the current pantry contents and the number of items held.
     */
    private static void viewPantry() {
        System.out.println("\n--- Pantry (" + pantry.size() + " items) ---");
        System.out.print(pantry.displayAll());
    }

    /**
     * Removes an ingredient by name. Reports whether the operation
     * actually removed anything.
     */
    private static void removeIngredient() {
        System.out.print("Name to remove: ");
        String name = scanner.nextLine().trim();
        Ingredient removed = pantry.remove(name);
        if (removed != null) System.out.println("Removed: " + removed);
        else                 System.out.println("Not found: " + name);
    }
}