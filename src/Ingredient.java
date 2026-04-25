/**
 * Ingredient — represents a single food item in the pantry.
 * Stores the item's name, quantity, and unit of measurement
 * (e.g., "2.5 cups of flour", or "12 eggs" for countable items).
 *
 * Why this class exists: Ingredients need a unified data type so that
 * both the Pantry (actual stock) and Recipe (required items) can
 * reference them consistently without duplicating field definitions.
 *
 * Author: Zachary Amith & Claude Opus 4.7
 */
public class Ingredient {

    private String name;
    private double quantity;
    private String unit;

    /**
     * Creates a new Ingredient with the given name, quantity, and unit.
     *
     * @param name     the ingredient's display name (e.g., "flour")
     * @param quantity amount owned or required
     * @param unit     unit of measurement (e.g., "cups", "grams"), or
     *                 empty string for countable items like eggs
     */
    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // --- Getters ---
    public String getName()     { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit()     { return unit; }

    // --- Setters (only for fields that change after creation) ---
    // Name is treated as the ingredient's identity, so no setter for it.
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setUnit(String unit)         { this.unit = unit; }

    /**
     * Formats the quantity, dropping the trailing ".0" for whole
     * numbers so output reads "12 eggs" rather than "12.0 eggs".
     */
    private String formatQty() {
        return (quantity == (long) quantity)
            ? String.valueOf((long) quantity)
            : String.valueOf(quantity);
    }

    /**
     * Human-readable representation used for pantry and shopping list
     * displays. Returns "12 eggs" for countable items (empty unit) or
     * "2.5 cups of flour" for measured items.
     */
    @Override
    public String toString() {
        if (unit == null || unit.isEmpty()) {
            return formatQty() + " " + name;
        }
        return formatQty() + " " + unit + " of " + name;
    }
}