package Fadishei.datastructures;

public class LinkedList<T> {

    private ListNode<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public void add(T data) {
        ListNode<T> newNode = new ListNode<>(data);
        if (head == null) {
            head = newNode;
        } else {
            ListNode<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    public boolean remove(T data) {
        if (head == null) return false;

        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return true;
        }

        ListNode<T> current = head;
        while (current.getNext() != null && !current.getNext().getData().equals(data)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(T data) {
        ListNode<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) return true;
            current = current.getNext();
        }
        return false;
    }

    public void printList() {
        ListNode<T> current = head;
        while (current != null) {
            System.out.print(current.getData() + " -> ");
            current = current.getNext();
        }
        System.out.println("null");
    }

    public int size() {
        return size;
    }
    
}

