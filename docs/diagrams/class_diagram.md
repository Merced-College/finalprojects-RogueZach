# Dog Food — Class Diagram

```mermaid
classDiagram
    class Main {
        -Scanner scanner
        -Pantry pantry
        -RecipeBook recipeBook
        +main(args)
        -addIngredient()
        -viewPantry()
        -removeIngredient()
        -viewRecipes()
        -findRecipes()
        -generateShoppingList()
        -cookRecipe()
        -undoLastAction()
        -searchRecipes()
    }

    class Ingredient {
        -String name
        -double quantity
        -String unit
        +getName() String
        +getQuantity() double
        +getUnit() String
        +setQuantity(double)
        +toString() String
    }

    class Pantry {
        -Entry[] buckets
        -int size
        -Stack~PantryAction~ history
        +put(name, ingredient)
        +get(name) Ingredient
        +contains(name) boolean
        +remove(name) Ingredient
        +deduct(recipe)
        +undo() PantryAction
        +displayAll() String
    }

    class Recipe {
        -String name
        -Ingredient[] required
        -int prepTimeMinutes
        -String category
        +matchScore(pantry) int
        +canCook(pantry) boolean
        +describeShortfall(pantry) String
        +toString() String
    }

    class RecipeBook {
        -LinkedList~Recipe~ recipes
        +add(recipe)
        +displayAll() String
        +displayWithScores(pantry) String
        +displayByCategory(pantry, cat) String
        +displayAboveScore(pantry, min) String
        +displaySorted(pantry) String
        +searchByName(query) String
    }

    class ShoppingList {
        -LinkedList~Ingredient~ items
        +add(ingredient)
        +remove(ingredient) boolean
        +displayAll() String
        +generateFor(recipe, pantry)$ ShoppingList
    }

    class LinkedList~T~ {
        -Node~T~ head
        -int size
        +add(value)
        +get(index) T
        +remove(target) boolean
        +getHead() Node
        +displayAll() String
    }

    class Stack~T~ {
        -Node~T~ top
        -int size
        +push(value)
        +pop() T
        +peek() T
        +isEmpty() boolean
    }

    class PantryAction {
        -String description
        -String[] keys
        -Ingredient[] before
        -boolean[] existed
        +getDescription() String
        +getKeys() String[]
        +getBefore() Ingredient[]
    }

    Main --> Pantry
    Main --> RecipeBook
    Pantry --> Stack : history
    Pantry ..> PantryAction : creates
    Pantry ..> Recipe : deduct uses
    Recipe --> Ingredient : required[]
    RecipeBook --> LinkedList : recipes
    ShoppingList --> LinkedList : items
    ShoppingList ..> Recipe : generateFor uses
    ShoppingList ..> Pantry : generateFor uses
    PantryAction --> Ingredient : before[]
```

## Relationship Notes

- **Main** owns one `Pantry` and one `RecipeBook` and routes user
  input to feature handlers
- **Pantry** is a hash table of ingredients with a `Stack<PantryAction>`
  for undo history
- **Recipe** holds a fixed array of `Ingredient` objects representing
  what's required
- **RecipeBook** stores recipes in a `LinkedList<Recipe>` and exposes
  filtered, sorted, and searched views
- **ShoppingList** wraps a `LinkedList<Ingredient>` and is built via
  the static factory `generateFor(recipe, pantry)`
- **PantryAction** is a snapshot-based record used to reverse changes;
  Pantry creates them, Stack stores them
- Solid arrows are owns/contains; dotted arrows are uses/depends-on