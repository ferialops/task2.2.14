package ru.vsu.cs.kunakhova_a_y;

public class MyLinkedList<T> {

    private MyLinkedListNode head = null;
    private MyLinkedListNode tail = null;
    private int size = 0;

    public void addLast(int value) {
        if (size == 0) {
            head = tail = new MyLinkedListNode(value);
        } else {
            tail.next = new MyLinkedListNode(value);
            tail = tail.next;
        }
        size++;
    }

    private void checkForEmpty() throws IndexOutOfBoundsException {
        if (size == 0) {
            throw new IndexOutOfBoundsException("List is empty");
        }
    }

    private MyLinkedListNode getNode(int index) {
        MyLinkedListNode curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr;
    }

    public void removeFirst() throws IndexOutOfBoundsException {
        checkForEmpty();
        head = head.next;
        if (size == 1) {
            tail = head;
        }
        size--;
    }

    public MyLinkedList<T> transformList(){
        MyLinkedList<T> transformedList = new MyLinkedList<>();
        MyLinkedListNode currObj = head;
        while (currObj != null)  {
            if (currObj.next == null) {
                transformedList.addLast(currObj.value);
                break;
            }
            transformedList.addLast(currObj.value + currObj.next.value);
            if (currObj.next.next != null)
                currObj = currObj.next.next;
            else {
                break;
            }
        }
        return transformedList;
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) removeFirst();
        else {
            MyLinkedListNode prev = getNode(index - 1);
            prev.next = prev.next.next;
            if (prev.next == null) {
                tail = prev;
            }
        }
        size--;
    }

    public int size() {
        return size;
    }

    public int get(int index) throws IndexOutOfBoundsException {
        return getNode(index).value;
    }

    private static class MyLinkedListNode {
        public int value;
        public MyLinkedListNode next;

        public MyLinkedListNode(int value, MyLinkedListNode next) {
            this.value = value;
            this.next = next;
        }

        public MyLinkedListNode(int value) {
            this(value, null);
        }
    }
}
