/**
 * RecipeBook — collection of all recipes the user can browse, search,
 * and match against the pantry. Backed by the custom generic
 * LinkedList so recipes can be added or removed dynamically as the
 * library grows.
 *
 * Author: Zachary Amith
 */
public class RecipeBook {

    private LinkedList<Recipe> recipes;

    public RecipeBook() {
        recipes = new LinkedList<>();
        loadStarterRecipes();
    }

    /**
     * Hardcodes a small starter set of recipes so the application
     * has content to demonstrate immediately. In a future version
     * these would be loaded from a file.
     */
    private void loadStarterRecipes() {
        recipes.add(new Recipe(
            "Scrambled Eggs",
            new Ingredient[] {
                new Ingredient("eggs", 3, ""),
                new Ingredient("butter", 1, "tbsp"),
                new Ingredient("salt", 1, "pinch")
            },
            10, "quick"
        ));

        recipes.add(new Recipe(
            "Pancakes",
            new Ingredient[] {
                new Ingredient("flour", 1.5, "cups"),
                new Ingredient("milk", 1.25, "cups"),
                new Ingredient("eggs", 1, ""),
                new Ingredient("sugar", 2, "tbsp"),
                new Ingredient("baking powder", 1, "tbsp")
            },
            20, "breakfast"
        ));

        recipes.add(new Recipe(
            "Vegan Pasta",
            new Ingredient[] {
                new Ingredient("pasta", 200, "grams"),
                new Ingredient("olive oil", 2, "tbsp"),
                new Ingredient("garlic cloves", 2, ""),
                new Ingredient("tomato sauce", 1, "cup")
            },
            25, "vegan"
        ));

        recipes.add(new Recipe(
            "Grilled Cheese",
            new Ingredient[] {
                new Ingredient("bread slices", 2, ""),
                new Ingredient("cheddar", 2, "slices"),
                new Ingredient("butter", 1, "tbsp")
            },
            8, "quick"
        ));

        recipes.add(new Recipe(
            "Omelette",
            new Ingredient[] {
                new Ingredient("eggs", 3, ""),
                new Ingredient("milk", 2, "tbsp"),
                new Ingredient("cheddar", 1, "slice"),
                new Ingredient("butter", 1, "tbsp")
            },
            12, "breakfast"
        ));
    }

    /** Adds a recipe to the book. */
    public void add(Recipe recipe) {
        recipes.add(recipe);
    }

    public LinkedList<Recipe> getRecipes() { return recipes; }
    public int size() { return recipes.size(); }

    /** Returns a numbered listing of every recipe in the book. */
    public String displayAll() {
        return recipes.displayAll();
    }

    /**
     * Walks every recipe and produces a listing showing each recipe's
     * match percentage against the given pantry. Iterates the linked
     * list directly via getHead so traversal is O(n) rather than the
     * O(n^2) cost of repeated get(index) calls.
     */
    public String displayWithScores(Pantry pantry) {
        if (recipes.isEmpty()) return "(no recipes)";
        StringBuilder sb = new StringBuilder();
        int i = 1;

        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            sb.append(i++).append(". ")
              .append(r.getName())
              .append(" — ").append(r.matchScore(pantry)).append("% match")
              .append(" [").append(r.getCategory()).append(", ")
              .append(r.getPrepTimeMinutes()).append(" min]\n");
            node = node.next;
        }
        return sb.toString();
    }

    /**
     * Same as displayWithScores but only includes recipes whose
     * category matches the given filter (case-insensitive). Useful
     * for narrowing results to "vegan", "quick", "breakfast", etc.
     */
    public String displayByCategory(Pantry pantry, String category) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            if (r.getCategory().equalsIgnoreCase(category)) {
                sb.append(i++).append(". ")
                  .append(r.getName())
                  .append(" — ").append(r.matchScore(pantry)).append("% match")
                  .append(" [").append(r.getPrepTimeMinutes()).append(" min]\n");
            }
            node = node.next;
        }
        return sb.length() == 0 ? "(no recipes in category: " + category + ")" : sb.toString();
    }
    
    /**
     * Same as displayWithScores but only includes recipes whose match
     * score meets or exceeds the given threshold. Useful for hiding
     * recipes the user is far from being able to make.
     */
    public String displayAboveScore(Pantry pantry, int minScore) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            int score = r.matchScore(pantry);
            if (score >= minScore) {
                sb.append(i++).append(". ")
                  .append(r.getName())
                  .append(" — ").append(score).append("% match")
                  .append(" [").append(r.getCategory()).append(", ")
                  .append(r.getPrepTimeMinutes()).append(" min]\n");
            }
            node = node.next;
        }
        return sb.length() == 0 ? "(no recipes at or above " + minScore + "%)" : sb.toString();
    }
}