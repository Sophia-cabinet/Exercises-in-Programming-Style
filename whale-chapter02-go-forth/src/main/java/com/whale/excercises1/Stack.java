package com.whale.excercises1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stack<E> {

    private int top;
    private E[] elements;
    private final int size;

    public Stack() {
        this(150000);
    }

    public Stack(int s) {
        size = s > 0 ? s : 10;
        top = -1;
        elements = (E[]) new Object[size]; // create array
    }

    /**
     *
     * element return and remove(last index)
     *
     * @return
     */
    public E pop() throws Exception {
        if (top == -1) // if stack is empty
            throw new Exception();

        return elements[top--]; // remove and return top element of Stack
    }

    public void push(E pushValue) throws Exception {
        if (top == size - 1) // if stack is full
            throw new Exception(String.format("Stack is full, cannot push %s", pushValue));

        elements[++top] = pushValue; // place pushValue on Stack
    }

    public void push(List pushValue) throws Exception {
        if (top == size - 1) // if stack is full
            throw new Exception(String.format("Stack is full, cannot push %s", pushValue));

        Iterator<String> iter = pushValue.iterator();
        while (iter.hasNext()) {
            push((E)iter.next());
        }
    }

    public void pushList(List pushValue) throws Exception {
        push((E)pushValue);
    }

    /**
     *
     * list extend
     *
     * @param pushElements
     */
    public void extend(List pushElements) {
        Iterator<Character> iter = pushElements.iterator();
        while (iter.hasNext()) {
            ((List<String>)elements[top]).add(String.valueOf(iter.next()));
        }
    }

    public E currentValue() {
        return elements[top];
    }
    /**
     *
     * element size return
     *
     * @return
     */
    public int len() {
        return this.top;
    }

//    /**
//     *
//     * list extend
//     *
//     * @param elements
//     */
//    public void extend(Object[] elements) {
//        extend(Arrays.asList(elements));
//    }

//    /**
//     *
//     * list extend
//     *
//     * @param elements
//     */
//    public void extend(List<Object> elements) {
//        this.stack.addAll(elements);
//    }
}
