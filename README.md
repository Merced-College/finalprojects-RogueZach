# Dog Food — Pantry Management & Recipe Application

## Description

Dog Food is a Java console application that answers two questions
every home cook asks: *what do I have?* and *what can I cook?*

Users maintain a digital inventory of their kitchen, and the program
cross-references that inventory against a built-in recipe book to
suggest meals they can make right now, generate shopping lists for
meals they want to make, and deduct ingredients automatically when
they cook. An undo feature lets users reverse any pantry change.

The application is built around three custom data structures (a
hash table, a linked list, and a stack), all implemented from
scratch.

## How to Run

From the project root:

```
javac src/*.java -d out
java -cp out Main
```

Requires Java 11 or later.

## Features

- **Add, view, and remove pantry ingredients** with quantities and units
- **Browse the built-in recipe book** of five starter recipes
- **Find recipes you can make** based on what's in your pantry, with
  match-percentage scoring
- **Filter recipes** by category (vegan, quick, breakfast) or by
  minimum match percentage
- **Sort recipes by match score** so the best matches show first
- **Search recipes by name** with case-insensitive partial matching
- **Generate a shopping list** for any recipe, showing exactly what
  you're missing and in what quantity
- **Cook a recipe** with one command — pantry quantities are
  deducted automatically
- **Undo any pantry action** (add, remove, or cook) with a single
  menu option

## Data Structures Used

| Structure | Class | Purpose |
|-----------|-------|---------|
| **Hash Table** | `Pantry` | Stores ingredients keyed by name for O(1) lookup. Uses separate chaining with 16 buckets. Critical for match scoring, which queries the pantry once per ingredient per recipe. |
| **Linked List** | `LinkedList<T>` | Generic singly linked list backing `RecipeBook` and `ShoppingList`. Chosen because both lists are dynamic — recipes and shopping items are added and removed frequently, sometimes from the middle. |
| **Stack** | `Stack<T>` | LIFO stack backing the pantry's undo history. Linked rather than array-backed so it grows without resize cost. |
| **Array** | inside `Recipe` | Fixed array of required ingredients. An array is honest here because a recipe's ingredient list doesn't change after definition. |

## Algorithms Used

| Algorithm | Location | Big-O |
|-----------|----------|-------|
| **Match Scoring** | `Recipe.matchScore` | O(M) per recipe, O(N·M) for the full book |
| **Inventory Deduction** | `Pantry.deduct` | O(M) per cook |
| **Merge Sort** | `RecipeBook.displaySorted` | O(N log N), worst-case included |
| **Linear Search** | `RecipeBook.searchByName` | O(N) |

## Project Structure

```
src/                Java source files
  Main.java         Console menu loop
  Ingredient.java   Single food item
  Pantry.java       Hash table inventory + undo
  Recipe.java       Meal definition + match scoring
  RecipeBook.java   LinkedList<Recipe> with filters and sort
  ShoppingList.java LinkedList<Ingredient> for missing items
  LinkedList.java   Custom generic singly linked list
  Stack.java        Custom generic LIFO stack
  PantryAction.java Reversible pantry change record

docs/               Algorithm explanations and diagrams
report/             Final written report
```

## Author

Zachary Amith