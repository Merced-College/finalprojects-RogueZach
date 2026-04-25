# Inventory Deduction

## Steps

1. Take a `Recipe` as input
2. Capture a snapshot of every required ingredient's pre-deduction
   state and push it as a single `PantryAction` onto the undo stack
3. For each ingredient in the recipe's required array:
   - Look up the current owned amount in the hash table
   - Compute `remaining = owned.quantity - required.quantity`
   - If `remaining <= 0`, remove the ingredient from the pantry
   - Otherwise, set the ingredient's quantity to `remaining`

## Explanation

When the user cooks a recipe, the pantry needs to actually shrink.
Deduction subtracts the required amount from the owned amount and
removes ingredients that hit zero so the pantry display stays clean.

The snapshot-and-push step makes the entire cook a single undoable
action. Pressing undo once after cooking restores all ingredients
to their pre-cook state, rather than forcing the user to undo each
ingredient separately.

## How it's used

- `Pantry.deduct(recipe)` — called from `Main.cookRecipe` after
  `Recipe.canCook` confirms the pantry has enough stock and the
  user confirms with y/n

## Big-O

| Operation | Time |
|-----------|------|
| Per cook | O(M) where M is required ingredients |
| Snapshot | O(M) for the parallel arrays |
| Each lookup/update | O(1) average via hash table |