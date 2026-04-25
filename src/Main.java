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
                case "6": generateShoppingList(); break;
                case "7": cookRecipe(); break;
                case "8": undoLastAction(); break;
                case "9": searchRecipes(); break;
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
        System.out.println("6. Generate Shopping List");
        System.out.println("7. Cook a Recipe");
        System.out.println("8. Undo Last Action");
        System.out.println("9. Search Recipes by Name");
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
     * between viewing all matches, filtering by category, or
     * filtering by a minimum match percentage.
     */
    private static void findRecipes() {
        System.out.println("\n--- Find Recipes ---");
        System.out.println("1. All recipes (with scores)");
        System.out.println("2. Filter by category");
        System.out.println("3. Filter by minimum match %");
        System.out.println("4. Sort by match score (best first)");
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
            case "3":
                System.out.print("Minimum match % (0-100): ");
                try {
                    int min = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print(recipeBook.displayAboveScore(pantry, min));
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid number.");
                }
                break;
            case "4":
                System.out.print(recipeBook.displaySorted(pantry));
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Prompts the user to pick a recipe by number, then prints a
     * shopping list of ingredients they still need to buy.
     */
    private static void generateShoppingList() {
        if (recipeBook.size() == 0) {
            System.out.println("No recipes available.");
            return;
        }

        System.out.println("\n--- Recipes ---");
        System.out.print(recipeBook.displayAll());
        System.out.print("Pick a recipe number: ");

        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }

        Recipe chosen = recipeBook.getRecipes().get(idx);
        if (chosen == null) {
            System.out.println("No recipe at that position.");
            return;
        }

        ShoppingList list = ShoppingList.generateFor(chosen, pantry);
        System.out.println("\n--- Shopping List for " + chosen.getName() + " ---");
        if (list.isEmpty()) {
            System.out.println("You have everything you need!");
        } else {
            System.out.print(list.displayAll());
        }
    }

    /**
     * Prompts the user to pick a recipe, verifies there's enough in
     * the pantry to actually cook it, asks for confirmation, then
     * deducts the required quantities. Does nothing if the recipe
     * isn't cookable.
     */
    private static void cookRecipe() {
        if (recipeBook.size() == 0) {
            System.out.println("No recipes available.");
            return;
        }

        System.out.println("\n--- Recipes ---");
        System.out.print(recipeBook.displayAll());
        System.out.print("Pick a recipe number: ");

        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }

        Recipe chosen = recipeBook.getRecipes().get(idx);
        if (chosen == null) {
            System.out.println("No recipe at that position.");
            return;
        }

        if (!chosen.canCook(pantry)) {
            System.out.println("You don't have enough to cook " + chosen.getName() + ":");
            System.out.print(chosen.describeShortfall(pantry));
            System.out.println("Try option 6 to generate a shopping list.");
            return;
        }

        System.out.print("Cook " + chosen.getName() + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        pantry.deduct(chosen);
        System.out.println("Cooked " + chosen.getName() + "! Pantry updated.");
    }
    
    /**
     * Reverses the most recent pantry mutation by popping the top
     * of the undo stack and replaying its before-snapshot. Reports
     * which action was undone or notes that history is empty.
     */
    private static void undoLastAction() {
        PantryAction undone = pantry.undo();
        if (undone == null) {
            System.out.println("Nothing to undo.");
        } else {
            System.out.println("Undone: " + undone.getDescription());
        }
    }
    /**
     * Prompts the user for a search term and prints every recipe
     * whose name contains the term (case-insensitive).
     */
    private static void searchRecipes() {
        System.out.print("Search term: ");
        String query = scanner.nextLine().trim();
        System.out.println("\n--- Search Results ---");
        System.out.print(recipeBook.searchByName(query));
    }
}