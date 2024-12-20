package Fadishei.datastructures;

public class ListNode<T> {
    private T data;
    private ListNode<T> next;
    public ListNode(T data) {
        this.setData(data);
        this.setNext(null);
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public ListNode<T> getNext() {
        return next;
    }
    public void setNext(ListNode<T> next) {
        this.next = next;
    }
}

