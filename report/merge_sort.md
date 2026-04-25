# Merge Sort

## Steps

1. Copy the recipe linked list into a `Recipe[]` array of size N
2. Compute the match score for each recipe once and store in a
   parallel `int[] scores` array
3. Recursively call `mergeSort(arr, scores, left, right)`:
   - Base case: if `left >= right`, return
   - Compute `mid = (left + right) / 2`
   - Recursively sort the left half and the right half
   - Call `merge` to combine the two sorted halves
4. `merge` walks both halves with two pointers and writes the
   higher-scoring recipe into the output position at each step,
   producing a descending-by-score run

## Explanation

Sorting recipes by match score means the user sees the best matches
first. Merge sort was chosen over quicksort because its worst-case
time is also O(N log N), giving predictable performance no matter
how the recipe book is ordered.

The algorithm sorts a snapshot copy of the linked list rather than
the list itself, so the underlying data structure is unchanged.
Match scores are computed up front and sorted in lockstep with the
recipe array — recomputing the score in every comparison would
multiply the cost.

## How it's used

- `RecipeBook.displaySorted(pantry)` — invoked from option 5 → 4
  in the main menu

## Big-O

| Operation | Time |
|-----------|------|
| Sort | O(N log N) average and worst case |
| Space | O(N) for the temporary arrays during merging |