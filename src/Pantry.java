/**
 * Pantry — stores the user's current ingredient inventory using a
 * custom hash table with separate chaining for collision handling.
 * Maintains an undo stack so every mutation can be reversed.
 *
 * Why a hash table: the core match-scoring algorithm must check, for
 * every ingredient in every recipe, whether the user owns it. A hash
 * table reduces the lookup step to O(1) on average, making scoring
 * feasible as the recipe library grows.
 *
 * Author: Zachary Amith
 */
public class Pantry {

    private static class Entry {
        String key;
        Ingredient value;
        Entry next;

        Entry(String key, Ingredient value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int CAPACITY = 16;
    private Entry[] buckets;
    private int size;

    // Undo stack: each PantryAction captures the pre-change state
    // of one or more ingredients so the mutation can be reversed.
    private Stack<PantryAction> history;

    public Pantry() {
        buckets = new Entry[CAPACITY];
        size = 0;
        history = new Stack<>();
    }

    private int hash(String key) {
        return Math.abs(key.toLowerCase().hashCode()) % buckets.length;
    }

    /**
     * Inserts or updates an ingredient and records the prior state
     * of this key on the undo stack.
     */
    public void put(String name, Ingredient ingredient) {
        recordSingle("Add/update " + name, name);
        putSilent(name, ingredient);
    }

    /**
     * Removes an ingredient by name and records the prior state on
     * the undo stack. Returns the removed ingredient, or null if
     * the key wasn't present (in which case nothing is recorded).
     */
    public Ingredient remove(String name) {
        if (!contains(name)) return null;
        recordSingle("Remove " + name, name);
        return removeSilent(name);
    }

    /** Performs a put without touching the undo stack. */
    void putSilent(String name, Ingredient ingredient) {
        int index = hash(name);
        Entry current = buckets[index];

        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                current.value = ingredient;
                return;
            }
            current = current.next;
        }

        Entry newEntry = new Entry(name, ingredient);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    /** Performs a remove without touching the undo stack. */
    Ingredient removeSilent(String name) {
        int index = hash(name);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                if (prev == null) buckets[index] = current.next;
                else              prev.next     = current.next;
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Captures the current state of a single ingredient key and
     * pushes it onto the undo stack as a reversible action.
     */
    private void recordSingle(String description, String name) {
        Ingredient existing = get(name);
        String[] keys = { name };
        Ingredient[] before = { existing };
        boolean[] existed = { existing != null };
        history.push(new PantryAction(description, keys, before, existed));
    }

    public Ingredient get(String name) {
        int index = hash(name);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) return current.value;
            current = current.next;
        }
        return null;
    }

    public boolean contains(String name) {
        return get(name) != null;
    }

    public int size() {
        return size;
    }

    /**
     * Reverses the most recent pantry action by popping it from the
     * history stack and restoring each affected ingredient to its
     * prior state. Returns the action that was undone, or null if
     * there is no history.
     */
    public PantryAction undo() {
        PantryAction action = history.pop();
        if (action == null) return null;

        String[] keys = action.getKeys();
        Ingredient[] before = action.getBefore();
        boolean[] existed = action.getExisted();

        for (int i = 0; i < keys.length; i++) {
            if (existed[i]) {
                putSilent(keys[i], before[i]);
            } else {
                removeSilent(keys[i]);
            }
        }

        return action;
    }

    public String displayAll() {
        if (size == 0) return "Pantry is empty.";
        StringBuilder sb = new StringBuilder();
        for (Entry bucket : buckets) {
            Entry current = bucket;
            while (current != null) {
                sb.append("- ").append(current.value).append("\n");
                current = current.next;
            }
        }
        return sb.toString();
    }
    
    /**
     * Deducts the given recipe's required quantities from this pantry.
     * Ingredients that fall to zero or below are removed entirely.
     *
     * Does not yet record an undo action — that is added in the next
     * commit, where cooking becomes a single composite undoable
     * action covering all affected ingredients.
     */
    public void deduct(Recipe recipe) {
        for (Ingredient required : recipe.getRequired()) {
            Ingredient owned = get(required.getName());
            if (owned == null) continue;

            double remaining = owned.getQuantity() - required.getQuantity();
            if (remaining <= 0) {
                removeSilent(required.getName());
            } else {
                owned.setQuantity(remaining);
            }
        }
    }
}