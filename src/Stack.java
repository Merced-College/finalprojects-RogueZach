/**
 * Stack — a custom generic LIFO stack built on a singly linked
 * structure. Used by the Pantry to track a history of reversible
 * actions so the user can undo mistakes.
 *
 * Why a linked structure rather than an array: the undo history
 * grows without a known upper bound as the user works. A linked
 * implementation gives O(1) push and pop without any resize cost.
 *
 * Implemented from scratch (not java.util.Stack) to satisfy the
 * project's custom data structure requirement.
 *
 * Author: Zachary Amith
 */
public class Stack<T> {

    /**
     * Internal node holding one value and a pointer to the node
     * beneath it in the stack.
     */
    private static class Node<T> {
        T value;
        Node<T> below;
        Node(T value) { this.value = value; }
    }

    private Node<T> top;
    private int size;

    /** Pushes a value onto the top of the stack in O(1). */
    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.below = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top value, or null if the stack is
     * empty. Runs in O(1).
     */
    public T pop() {
        if (top == null) return null;
        T value = top.value;
        top = top.below;
        size--;
        return value;
    }

    /** Returns the top value without removing it, or null if empty. */
    public T peek() {
        return top == null ? null : top.value;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}