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
}