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
 * Author: Zachary Amith
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

    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }
}