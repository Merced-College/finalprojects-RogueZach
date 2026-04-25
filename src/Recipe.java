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
    /**
     * Calculates what percentage of this recipe's required ingredients
     * are currently in the given pantry. Returns 0–100.
     *
     * Algorithm: iterate through the required array (O(M)) and perform
     * an O(1) hash table lookup per ingredient, giving overall O(M)
     * per recipe. With N recipes in the book, scoring the entire
     * library costs O(N * M).
     *
     * Quantity is intentionally not checked here — the user might
     * still want to see "you have 4 of 5 ingredients for this recipe"
     * even if one is short. A stricter check that validates quantity
     * is implemented separately for the cook step (Phase 3).
     */
    public int matchScore(Pantry pantry) {
        if (required.length == 0) return 100;
        int owned = 0;
        for (Ingredient ing : required) {
            if (pantry.contains(ing.getName())) owned++;
        }
        return (int) Math.round(100.0 * owned / required.length);
    }
    
    /**
     * Returns true only if every required ingredient is in the pantry
     * AND the pantry has at least the required quantity of each.
     * Used by the cook step, where a partial match isn't enough —
     * you can't cook pancakes with half the flour you need.
     *
     * Algorithm: iterate the required array (O(M)) with O(1) hash
     * lookups per ingredient. Short-circuits on the first failure.
     */
    public boolean canCook(Pantry pantry) {
        for (Ingredient required : required) {
            Ingredient owned = pantry.get(required.getName());
            if (owned == null) return false;
            if (owned.getQuantity() < required.getQuantity()) return false;
        }
        return true;
    }
    
    /**
     * Produces a human-readable description of what's missing or
     * short for this recipe. Returns an empty string if the recipe
     * can be cooked as-is. Used by the cook step to give the user
     * actionable feedback instead of a vague "not enough" message.
     */
    public String describeShortfall(Pantry pantry) {
        StringBuilder sb = new StringBuilder();
        for (Ingredient required : required) {
            Ingredient owned = pantry.get(required.getName());
            if (owned == null) {
                sb.append("  - missing: ").append(required).append("\n");
            } else if (owned.getQuantity() < required.getQuantity()) {
                double shortfall = required.getQuantity() - owned.getQuantity();
                sb.append("  - need ").append(shortfall)
                  .append(" more ").append(required.getUnit().isEmpty() ? "" : required.getUnit() + " of ")
                  .append(required.getName()).append("\n");
            }
        }
        return sb.toString();
    }
}