# Dog Food — Pantry Management & Recipe Application

## Description
Dog Food is a pantry management and recipe application that answers
"what do I have?" and "what can I cook?" Users maintain a digital
inventory of their kitchen, and the program cross-references that
inventory against a recipe library to suggest meals they can make,
reducing food waste and grocery spending.

## How to Run
1. Navigate to the `src/` directory
2. Compile: `javac *.java`
3. Run: `java Main`

## Features
- Add, update, and remove pantry ingredients
- Match recipes against the current pantry and rank by coverage
- Generate shopping lists for recipes with missing ingredients
- Undo recent pantry changes
- Filter recipes by category and prep time

## Data Structures Used
- **Hash Table** (custom) — `Pantry` class, O(1) ingredient lookup
- **Linked List** (custom, generic) — `RecipeBook` and `ShoppingList`
- **Stack** (custom) — undo history for pantry actions

## Algorithms Used
- **Match Scoring** — ranks recipes by pantry coverage (%)
- **Merge Sort** — sorts ranked recipes by match score
- **Linear Search** — finds recipes by name or category
- **Inventory Deduction** — updates pantry after cooking

## Author
Zachary Amith