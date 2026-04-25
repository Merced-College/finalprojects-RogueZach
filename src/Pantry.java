/**
 * Pantry — stores the user's current ingredient inventory using a
 * custom hash table with separate chaining for collision handling.
 *
 * Why a hash table: the core match-scoring algorithm must check, for
 * every ingredient in every recipe, whether the user owns it. With N
 * recipes and M ingredients each, a list-based pantry would cost
 * O(N * M * P) where P is pantry size. A hash table reduces the
 * lookup step to O(1) on average, making scoring feasible as the
 * recipe library grows.
 *
 * Collision strategy: separate chaining. Each bucket is the head of
 * a singly linked chain of Entry nodes. When two different keys hash
 * to the same bucket, the newer one is prepended to the chain.
 *
 * Author: Zachary Amith
 */
public class Pantry {

    /**
     * Entry — internal node type for the hash table's collision chains.
     * Stores the key/value pair plus a pointer to the next entry in
     * the same bucket.
     */
    private static class Entry {
        String key;
        Ingredient value;
        Entry next;

        Entry(String key, Ingredient value) {
            this.key = key;
            this.value = value;
        }
    }

    // Fixed-size bucket array. Could be resized dynamically in v2, but
    // 16 buckets is plenty for a personal pantry and keeps the code
    // focused on the hashing concept itself.
    private static final int CAPACITY = 16;
    private Entry[] buckets;
    private int size;

    public Pantry() {
        buckets = new Entry[CAPACITY];
        size = 0;
    }

    /**
     * Hashes a key to a bucket index. Normalizes case so "Flour" and
     * "flour" map to the same bucket, and uses Math.abs to handle the
     * negative values that Java's hashCode() can return.
     */
    private int hash(String key) {
        return Math.abs(key.toLowerCase().hashCode()) % buckets.length;
    }

    /**
     * Inserts or updates an ingredient in the pantry. If the name
     * already exists, its value is overwritten rather than duplicated.
     */
    public void put(String name, Ingredient ingredient) {
        int index = hash(name);
        Entry current = buckets[index];

        // Walk the bucket's chain looking for an existing key
        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                current.value = ingredient;  // overwrite
                return;
            }
            current = current.next;
        }

        // Key not found — prepend a new entry to the chain
        Entry newEntry = new Entry(name, ingredient);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    /**
     * Retrieves an ingredient by name. Returns null if not found.
     */
    public Ingredient get(String name) {
        int index = hash(name);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) return current.value;
            current = current.next;
        }
        return null;
    }

    /**
     * Convenience wrapper around get() for presence checks.
     */
    public boolean contains(String name) {
        return get(name) != null;
    }

    /**
     * Removes an ingredient by name. Returns the removed ingredient,
     * or null if it wasn't present.
     */
    public Ingredient remove(String name) {
        int index = hash(name);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                // Unlink the node from the chain
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

    public int size() {
        return size;
    }

    /**
     * Builds a readable listing of every ingredient currently in the
     * pantry by walking each bucket's chain in turn. Used by Main's
     * "View Pantry" menu option.
     */
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
     * Ingredients that fall to zero or below are removed entirely so
     * displayAll doesn't show empty entries.
     *
     * Assumes Recipe.canCook has already returned true — this method
     * does not re-validate, so calling it on an uncookable recipe
     * will produce negative quantities or remove partial stock.
     *
     * Algorithm: iterate the required array (O(M)) and perform O(1)
     * hash lookups and updates per ingredient, giving O(M) overall.
     */
    public void deduct(Recipe recipe) {
        for (Ingredient required : recipe.getRequired()) {
            Ingredient owned = get(required.getName());
            if (owned == null) continue;  // defensive: shouldn't happen if canCook passed

            double remaining = owned.getQuantity() - required.getQuantity();
            if (remaining <= 0) {
                remove(required.getName());
            } else {
                owned.setQuantity(remaining);
            }
        }
    }
}