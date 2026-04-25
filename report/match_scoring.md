# Match Scoring

## Steps

1. Take a `Recipe` and a `Pantry` as input
2. If the recipe has zero required ingredients, return 100
3. Initialize a counter `owned = 0`
4. For each ingredient in the recipe's required array:
   - Look up the ingredient by name in the pantry hash table
   - If present, increment `owned`
5. Return `round(100.0 * owned / required.length)` as an integer percentage

## Explanation

Match scoring answers the question "how close am I to being able to
make this recipe?" without caring about exact quantities. The user
might still find it useful to know they have 4 of 5 ingredients for
a recipe even if one is short — that's a different question than
whether they can actually cook it (handled by `canCook`).

The algorithm relies on the pantry being a hash table. Each
`contains` call is O(1) on average, so checking M ingredients takes
O(M) total. Scoring the entire recipe book of N recipes is O(N · M).

## How it's used

- `Recipe.matchScore(pantry)` — single recipe
- `RecipeBook.displayWithScores(pantry)` — every recipe with score
- `RecipeBook.displayByCategory` and `displayAboveScore` — filtered views
- `RecipeBook.displaySorted` — feeds scores into merge sort

## Big-O

| Operation | Time |
|-----------|------|
| Single recipe | O(M) where M is required ingredients |
| Full recipe book | O(N · M) where N is total recipes |
| Space | O(1) — just a counter |