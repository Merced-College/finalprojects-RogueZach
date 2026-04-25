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