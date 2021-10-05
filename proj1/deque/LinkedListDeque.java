package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {
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

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item, sentFront, sentFront.next);
        sentFront.next = newNode;
        newNode.next.prev = newNode;
        ++size;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item, sentBack.prev, sentBack);
        sentBack.prev = newNode;
        newNode.prev.next = newNode;
        ++size;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node curr = sentFront.next;
        while(curr != null) {
            System.out.print(curr.item);
            System.out.print(' '); //TODO: Should I strip the last space?
            curr = curr.next;
        }
        System.out.print('\n');
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node curr = sentFront.next;

        @Override
        public boolean hasNext() {
            return curr != sentBack;
        }

        @Override
        public T next() {
            T item = curr.item;
            curr = curr.next;
            return item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    // NOTE: https://stackoverflow.com/questions/24016962/java8-why-is-it-forbidden-to-define-a-default-method-for-a-method-from-java-lan/24026292
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o == this)
            return true;
        if(!(o instanceof Deque))
            return false;
        Deque<T> other = (Deque<T>)o;
        if(size() != other.size())
            return false;
        Iterator<T> iter1 = iterator();
        Iterator<T> iter2 = other.iterator();
        while(iter1.hasNext()) {
            T curr1 = iter1.next();
            T curr2 = iter2.next();
            if(!curr1.equals(curr2))
                return false;
        }
        return true;
    }
}
