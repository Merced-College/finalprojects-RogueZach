/**
 * PantryAction — records a single reversible change to the pantry.
 * Each action stores the ingredient keys affected and their state
 * before the change, so undo can restore the pantry to its prior
 * condition by replaying those previous values.
 *
 * Design rationale: rather than encoding separate action types
 * (ADD / REMOVE / DEDUCT), we store before-snapshots keyed by
 * ingredient name. Undo is then uniform — for each key, either
 * restore the ingredient (if it existed) or remove it (if it
 * didn't). This keeps the action format simple enough to cover
 * single-ingredient changes and multi-ingredient cooks with the
 * same shape.
 *
 * Author: Zachary Amith
 */
public class PantryAction {

    private String description;
    private String[] keys;
    private Ingredient[] before;
    private boolean[] existed;

    public PantryAction(String description, String[] keys,
                        Ingredient[] before, boolean[] existed) {
        this.description = description;
        this.keys = keys;
        this.before = before;
        this.existed = existed;
    }

    public String getDescription()  { return description; }
    public String[] getKeys()       { return keys; }
    public Ingredient[] getBefore() { return before; }
    public boolean[] getExisted()   { return existed; }
}