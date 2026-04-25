/**
 * Recipe — represents a single meal with its required ingredients,
 * prep time, and category (e.g., vegan, quick, dessert).
 *
 * Why an array for ingredients: a recipe's required ingredients are
 * fixed once the recipe is defined — they don't grow or shrink. An
 * array gives O(1) indexed access for the match-scoring algorithm
 * without the overhead of a list structure.
 *
 * Author: Zachary Amith
 */
public class Recipe {

    private String name;
    private Ingredient[] required;
    private int prepTimeMinutes;
    private String category;

    /**
     * Creates a Recipe with the given name, required ingredients,
     * prep time in minutes, and category.
     *
     * @param name             display name of the recipe
     * @param required         fixed array of required ingredients
     * @param prepTimeMinutes  estimated prep + cook time
     * @param category         classification tag (vegan, quick, etc.)
     */
    public Recipe(String name, Ingredient[] required,
                  int prepTimeMinutes, String category) {
        this.name = name;
        this.required = required;
        this.prepTimeMinutes = prepTimeMinutes;
        this.category = category;
    }

    public String getName()           { return name; }
    public Ingredient[] getRequired() { return required; }
    public int getPrepTimeMinutes()   { return prepTimeMinutes; }
    public String getCategory()       { return category; }

    /**
     * Multi-line representation of the recipe used in menu listings.
     * Shows the name, category, prep time, and each required
     * ingredient on its own line.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
          .append(" [").append(category).append(", ")
          .append(prepTimeMinutes).append(" min]\n");
        sb.append("  Ingredients:\n");
        for (Ingredient ing : required) {
            sb.append("    - ").append(ing).append("\n");
        }
        return sb.toString();
    }
}