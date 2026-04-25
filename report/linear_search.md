# Linear Search

## Steps

1. Take a query string as input
2. Convert the query to lowercase
3. Walk the recipe linked list from the head
4. For each recipe, check whether the lowercased name contains the
   query as a substring
5. If yes, append the recipe to the result builder
6. Return the formatted result, or a "no matches" message if none
   were found

## Explanation

Search lets the user find a recipe by typing part of its name. A
linear search is the right choice here because the recipe book is
small (single digits to maybe a few dozen entries) and unsorted.
Binary search would require maintaining sort order on every insert,
which isn't worth the cost at this scale.

The substring match is case-insensitive so users don't have to
match the recipe's original capitalization.

## How it's used

- `RecipeBook.searchByName(query)` — invoked from option 9 in the
  main menu

## Big-O

| Operation | Time |
|-----------|------|
| Search | O(N) where N is total recipes |
| Per-recipe substring check | O(L) where L is name length |
| Space | O(K) for the result string where K is matches |