/**
 * PantryAction — records a single reversible change to the pantry.
 * Each action stores the list of ingredients affected and their
 * state before the change, so undo can restore the pantry to its
 * prior condition by replaying those previous values.
 *
 * Design rationale: rather than encoding separate action types
 * (ADD / REMOVE / DEDUCT), we store before-snapshots of every
 * affected ingredient. Undo is then uniform — for each snapshot,
 * either restore the ingredient (if it existed) or remove it (if
 * it didn't). This keeps the action format simple enough to cover
 * single-ingredient changes and multi-ingredient cooks with the
 * same shape.
 *
 * Author: Zachary Amith
 */
public class PantryAction {

    private String description;
    private Ingredient[] before;
    private boolean[] existed;

    /**
     * Creates a new action.
     *
     * @param description  human-readable label (e.g., "Add flour",
     *                     "Cook Scrambled Eggs")
     * @param before       snapshot of each affected ingredient's
     *                     state before the change; a null entry
     *                     means the ingredient didn't exist
     * @param existed      parallel array indicating whether each
     *                     ingredient was present before the action
     */
    public PantryAction(String description, Ingredient[] before, boolean[] existed) {
        this.description = description;
        this.before = before;
        this.existed = existed;
    }

    public String getDescription()   { return description; }
    public Ingredient[] getBefore()  { return before; }
    public boolean[] getExisted()    { return existed; }
}