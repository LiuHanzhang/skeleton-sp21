package deque;

public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T elem, Node p, Node n) {
            item = elem;
            prev = p;
            next = n;
        }
        public Node(Node p, Node n) {
            prev = p;
            next = n;
        }
    }

    private Node sentFront;
    private Node sentBack;
    private int size;

    public LinkedListDeque() {
        sentFront = new Node(null, null);
        sentBack = new Node(sentFront, null);
        sentFront.next = sentBack;
        size = 0;
    }

    public void addFirst(T item) {
        Node newNode = new Node(item, sentFront, sentFront.next);
        sentFront.next = newNode;
        newNode.next.prev = newNode;
        ++size;
    }

    public void addLast(T item) {
        Node newNode = new Node(item, sentBack.prev, sentBack);
        sentBack.prev = newNode;
        newNode.prev.next = newNode;
        ++size;
    }

    public T removeFirst() {
        if(isEmpty())
            return null;
        Node removed = sentFront.next;
        sentFront.next = removed.next;
        removed.next.prev = sentFront;
        T elem = removed.item;
        removed = null;
        --size;
        return elem;
    }

    public T removeLast() {
        if(isEmpty())
            return null;
        Node removed = sentBack.prev;
        sentBack.prev = removed.prev;
        removed.prev.next = sentBack;
        T elem = removed.item;
        removed = null;
        --size;
        return elem;
    }

    public T get(int index) {
        if(index < 0 || size() <= index)
            return null;
        Node curr = sentFront.next;
        for(int i = 0; i != index; ++i) {
            curr = curr.next;
        }
        return curr.item;
    }

    private T getRecursiveHelper(int index, Node curr) {
        if(index == 0) {
            return curr.item;
        } else {
            return getRecursiveHelper(index - 1, curr.next);
        }
    }

    public T getRecursive(int index) {
        if(index < 0 || size() <= index)
            return null;
        else
            return getRecursiveHelper(index, sentFront.next);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node curr = sentFront.next;
        while(curr != null) {
            System.out.print(curr.item);
            System.out.print(' '); //TODO: Should I strip the last space?
            curr = curr.next;
        }
        System.out.print('\n');
    }
}
