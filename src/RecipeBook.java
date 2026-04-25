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
}