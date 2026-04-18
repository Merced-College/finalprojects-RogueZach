public class Pantry {

    // Inner node class for separate chaining
    private static class Entry {
        String key;
        Ingredient value;
        Entry next;

        Entry(String key, Ingredient value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] buckets;
    private int size;
    private static final int CAPACITY = 16;

    public Pantry() {
        buckets = new Entry[CAPACITY];
        size = 0;
    }

    // Hash function: normalize case so "Flour" and "flour" collide correctly
    private int hash(String key) {
        return Math.abs(key.toLowerCase().hashCode()) % buckets.length;
    }

    public void put(String name, Ingredient ingredient) {
        int index = hash(name);
        Entry current = buckets[index];

        // If key already exists, overwrite its value
        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                current.value = ingredient;
                return;
            }
            current = current.next;
        }

        // Otherwise insert new entry at head of the bucket chain
        Entry newEntry = new Entry(name, ingredient);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
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

    public Ingredient remove(String name) {
        int index = hash(name);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(name)) {
                if (prev == null) buckets[index] = current.next;
                else              prev.next = current.next;
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

    // Walk every bucket and build a display string
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
}