/**
 * LinkedList — a custom singly linked list that can store any type T.
 *
 * Why a linked list: the parts of Dog Food that use this class
 * (RecipeBook and ShoppingList) are dynamic — items are added and
 * removed frequently, often from the middle. A linked list handles
 * those operations without shifting elements like an array would
 * require.
 *
 * Implemented from scratch (not java.util.LinkedList) to satisfy the
 * project's custom data structure requirement.
 *
 * Author: Zachary Amith & Claude Opus 4.7
 */
public class LinkedList<T> {

    /**
     * Internal node holding one value and a pointer to the next node.
     * Package-private so other classes in this project can traverse
     * the list directly when needed (e.g., RecipeBook iteration).
     */
    static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private Node<T> head;
    private int size;

    /**
     * Adds a value at the end of the list. Walks to the tail rather
     * than caching it; acceptable here because the lists in this
     * project stay small (recipe book, shopping list).
     */
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        size++;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the value at the given index, or null if out of bounds.
     * Walks the chain from the head, so cost is O(index).
     */
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.value;
    }

    /**
     * Removes the first node whose value equals the given target.
     * Returns true if a node was removed, false if no match was found.
     */
    public boolean remove(T target) {
        if (head == null) return false;

        if (head.value.equals(target)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> prev = head;
        Node<T> current = head.next;
        while (current != null) {
            if (current.value.equals(target)) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the head node so callers can walk the list directly.
     * Used by RecipeBook to iterate in O(n) instead of repeatedly
     * calling get(index), which would cost O(n^2) overall.
     */
    public Node<T> getHead() { return head; }

    /**
     * Builds a numbered, multi-line string by walking every node and
     * appending its toString. Returns "(empty)" if the list is empty.
     */
    public String displayAll() {
        if (head == null) return "(empty)";
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        int i = 1;
        while (current != null) {
            sb.append(i++).append(". ").append(current.value).append("\n");
            current = current.next;
        }
        return sb.toString();
    }
}