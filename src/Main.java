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
    private static final RecipeBook recipeBook = new RecipeBook();

    public static void main(String[] args) {
        System.out.println("Welcome to Dog Food - Pantry Manager");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addIngredient(); break;
                case "2": viewPantry(); break;
                case "3": removeIngredient(); break;
                case "4": viewRecipes(); break;
                case "5": findRecipes(); break;
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
        System.out.println("4. View Recipes");
        System.out.println("5. Find Recipes I Can Make");
        System.out.println("0. Quit");
        System.out.print("Choice: ");
    }

    private static void addIngredient() {
        System.out.print("Ingredient name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Quantity: ");
        String qtyStr = scanner.nextLine().trim();
        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity.");
            return;
        }

        System.out.print("Unit (press Enter for whole/countable items): ");
        String unit = scanner.nextLine().trim();

        pantry.put(name, new Ingredient(name, qty, unit));
        System.out.println("Added: " + name);
    }

    private static void viewPantry() {
        System.out.println("\n--- Pantry (" + pantry.size() + " items) ---");
        System.out.print(pantry.displayAll());
    }

    private static void removeIngredient() {
        System.out.print("Name to remove: ");
        String name = scanner.nextLine().trim();
        Ingredient removed = pantry.remove(name);
        if (removed != null) {
            System.out.println("Removed: " + removed);
        } else {
            System.out.println("Not found: " + name);
        }
    }

    private static void viewRecipes() {
        System.out.println("\n--- Recipes (" + recipeBook.size() + ") ---");
        System.out.print(recipeBook.displayAll());
    }

    /**
     * Sub-menu for the "Find Recipes" feature. Lets the user choose
     * between viewing all matches or filtering by category.
     */
    private static void findRecipes() {
        System.out.println("\n--- Find Recipes ---");
        System.out.println("1. All recipes (with scores)");
        System.out.println("2. Filter by category");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print(recipeBook.displayWithScores(pantry));
                break;
            case "2":
                System.out.print("Category (e.g., vegan, quick, breakfast): ");
                String cat = scanner.nextLine().trim();
                System.out.print(recipeBook.displayByCategory(pantry, cat));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}