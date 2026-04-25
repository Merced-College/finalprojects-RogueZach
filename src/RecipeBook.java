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

    /**
     * Walks every recipe and produces a listing showing each recipe's
     * match percentage against the given pantry. Iterates the linked
     * list directly via getHead so traversal is O(n) rather than the
     * O(n^2) cost of repeated get(index) calls.
     */
    public String displayWithScores(Pantry pantry) {
        if (recipes.isEmpty()) return "(no recipes)";
        StringBuilder sb = new StringBuilder();
        int i = 1;

        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            sb.append(i++).append(". ")
              .append(r.getName())
              .append(" — ").append(r.matchScore(pantry)).append("% match")
              .append(" [").append(r.getCategory()).append(", ")
              .append(r.getPrepTimeMinutes()).append(" min]\n");
            node = node.next;
        }
        return sb.toString();
    }

    /**
     * Same as displayWithScores but only includes recipes whose
     * category matches the given filter (case-insensitive). Useful
     * for narrowing results to "vegan", "quick", "breakfast", etc.
     */
    public String displayByCategory(Pantry pantry, String category) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            if (r.getCategory().equalsIgnoreCase(category)) {
                sb.append(i++).append(". ")
                  .append(r.getName())
                  .append(" — ").append(r.matchScore(pantry)).append("% match")
                  .append(" [").append(r.getPrepTimeMinutes()).append(" min]\n");
            }
            node = node.next;
        }
        return sb.length() == 0 ? "(no recipes in category: " + category + ")" : sb.toString();
    }

    /**
     * Same as displayWithScores but only includes recipes whose match
     * score meets or exceeds the given threshold. Useful for hiding
     * recipes the user is far from being able to make.
     */
    public String displayAboveScore(Pantry pantry, int minScore) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            int score = r.matchScore(pantry);
            if (score >= minScore) {
                sb.append(i++).append(". ")
                  .append(r.getName())
                  .append(" — ").append(score).append("% match")
                  .append(" [").append(r.getCategory()).append(", ")
                  .append(r.getPrepTimeMinutes()).append(" min]\n");
            }
            node = node.next;
        }
        return sb.length() == 0 ? "(no recipes at or above " + minScore + "%)" : sb.toString();
    }
    /**
     * Returns the recipes sorted by match score (highest first) and
     * formatted as a numbered listing. Uses a custom merge sort
     * implementation rather than Java's built-in sort to satisfy the
     * project's algorithm requirement.
     *
     * Algorithm: copy the linked list into an array (O(n)), run
     * merge sort on the array (O(n log n)), then format the result.
     * Merge sort was chosen over quicksort because its worst-case
     * complexity is also O(n log n), giving predictable performance
     * regardless of input order.
     */
    public String displaySorted(Pantry pantry) {
        int n = recipes.size();
        if (n == 0) return "(no recipes)";

        // Copy the linked list contents into an array for sorting
        Recipe[] arr = new Recipe[n];
        LinkedList.Node<Recipe> node = recipes.getHead();
        for (int i = 0; i < n; i++) {
            arr[i] = node.value;
            node = node.next;
        }

        // Compute scores once up front to avoid recomputing during
        // every comparison in the sort
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) scores[i] = arr[i].matchScore(pantry);

        mergeSort(arr, scores, 0, n - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i + 1).append(". ")
              .append(arr[i].getName())
              .append(" — ").append(scores[i]).append("% match")
              .append(" [").append(arr[i].getCategory()).append(", ")
              .append(arr[i].getPrepTimeMinutes()).append(" min]\n");
        }
        return sb.toString();
    }

    /**
     * Recursive merge sort entry point. Sorts arr[left..right] and
     * its parallel scores array in descending order of score.
     */
    private void mergeSort(Recipe[] arr, int[] scores, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, scores, left, mid);
        mergeSort(arr, scores, mid + 1, right);
        merge(arr, scores, left, mid, right);
    }

    /**
     * Merges two sorted halves arr[left..mid] and arr[mid+1..right]
     * into a single descending-by-score run. Operates on both the
     * recipe and score arrays in lockstep so they stay aligned.
     */
    private void merge(Recipe[] arr, int[] scores,
                       int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Recipe[] leftArr = new Recipe[n1];
        int[] leftScores = new int[n1];
        Recipe[] rightArr = new Recipe[n2];
        int[] rightScores = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
            leftScores[i] = scores[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
            rightScores[j] = scores[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Descending: take whichever side has the higher score
            if (leftScores[i] >= rightScores[j]) {
                arr[k] = leftArr[i];
                scores[k] = leftScores[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                scores[k] = rightScores[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = leftArr[i];
            scores[k++] = leftScores[i++];
        }
        while (j < n2) {
            arr[k] = rightArr[j];
            scores[k++] = rightScores[j++];
        }
    }
    /**
     * Searches the recipe book for any recipe whose name contains
     * the given query (case-insensitive). Returns a formatted listing
     * of matches, or a "no matches" message if none are found.
     *
     * Algorithm: linear search. Walks every node in the linked list
     * and tests each recipe name against the query, giving O(n)
     * worst case. Linear search was chosen because the recipe book
     * is small and unsorted; binary search would require maintaining
     * a sorted order on every insert, which isn't worth it for this
     * data size.
     */
    public String searchByName(String query) {
        if (query == null || query.isEmpty()) return "(empty query)";
        String needle = query.toLowerCase();

        StringBuilder sb = new StringBuilder();
        int matches = 0;
        LinkedList.Node<Recipe> node = recipes.getHead();
        while (node != null) {
            Recipe r = node.value;
            if (r.getName().toLowerCase().contains(needle)) {
                matches++;
                sb.append(matches).append(". ")
                  .append(r.getName())
                  .append(" [").append(r.getCategory()).append(", ")
                  .append(r.getPrepTimeMinutes()).append(" min]\n");
            }
            node = node.next;
        }

        return matches == 0
            ? "(no recipes match \"" + query + "\")"
            : sb.toString();
    }
}