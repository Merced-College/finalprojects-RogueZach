/**
 * ShoppingList — a list of ingredients the user needs to buy for a
 * recipe they want to cook but cannot fully make yet. Backed by the
 * custom generic LinkedList so items can be added as the user
 * browses recipes and removed one by one as they shop.
 *
 * Why a linked list: a shopping list is highly dynamic, and items
 * are typically removed from the middle as the user finds them in
 * the store. The linked list handles those mid-list operations
 * without shifting elements like an array would require.
 *
 * Author: Zachary Amith
 */
public class ShoppingList {

    private LinkedList<Ingredient> items;

    public ShoppingList() {
        items = new LinkedList<>();
    }

    /** Adds an ingredient to the list. */
    public void add(Ingredient ingredient) {
        items.add(ingredient);
    }

    /** Removes an ingredient by reference equality (uses equals()). */
    public boolean remove(Ingredient ingredient) {
        return items.remove(ingredient);
    }

    public int size() { return items.size(); }
    public boolean isEmpty() { return items.isEmpty(); }

    /** Returns a numbered listing of every item on the list. */
    public String displayAll() {
        return items.displayAll();
    }
    
    /**
     * Builds a ShoppingList containing every ingredient from the
     * given recipe that is either missing from the pantry or present
     * in insufficient quantity.
     *
     * Algorithm: iterate the recipe's required array (O(M)) and
     * perform an O(1) hash table lookup per ingredient. For each
     * missing or short ingredient, add a new Ingredient to the list
     * representing the amount the user still needs to buy.
     */
    public static ShoppingList generateFor(Recipe recipe, Pantry pantry) {
        ShoppingList list = new ShoppingList();

        for (Ingredient required : recipe.getRequired()) {
            Ingredient owned = pantry.get(required.getName());

            if (owned == null) {
                // Not in pantry at all — need the full required amount
                list.add(new Ingredient(
                    required.getName(),
                    required.getQuantity(),
                    required.getUnit()
                ));
            } else if (owned.getQuantity() < required.getQuantity()) {
                // Have some but not enough — need the difference
                double shortfall = required.getQuantity() - owned.getQuantity();
                list.add(new Ingredient(
                    required.getName(),
                    shortfall,
                    required.getUnit()
                ));
            }
        }

        return list;
    }
}