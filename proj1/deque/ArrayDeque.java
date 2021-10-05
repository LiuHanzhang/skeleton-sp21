package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int nextFirst;
    private int nextLast;
    private final int RESIZE_FACTOR;
    private final int LOAD_FACTOR; // Actual load factor is its reciprocal.

    public ArrayDeque() {
        size = 0;
        capacity = 8;
        items = (T[]) new Object[capacity];
        nextFirst = 4;
        nextLast = 5;
        RESIZE_FACTOR = 2;
        LOAD_FACTOR = 4;
    }

    private boolean full() {
        return (nextFirst == (nextLast + 1) % capacity);
    }

    private boolean needShrink() {
        return (capacity >= 16) && ((size - 1) * LOAD_FACTOR < capacity);
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int new_capacity) {
        T[] a = (T[]) new Object[new_capacity];
        if(nextLast > nextFirst) { // If array doesn't circulate
            System.arraycopy(items, nextFirst, a, 0, size + 2);
            nextFirst = 0;
            nextLast = size + 1;
        } else { // If circulate
            System.arraycopy(items, nextFirst, a, 0, capacity - nextFirst);
            System.arraycopy(items, 0, a, capacity - nextFirst, nextLast + 1);
            nextFirst = 0;
            nextLast = size + 1;
        }
        capacity = new_capacity;
        items = a;
    }

    @Override
    public void addFirst(T item) {
        if(full())
            resize(capacity * RESIZE_FACTOR);
        items[nextFirst] = item;
        nextFirst = (nextFirst == 0) ? (capacity - 1) : nextFirst - 1;
        ++size;
    }

    @Override
    public void addLast(T item) {
        if(full())
            resize(capacity * RESIZE_FACTOR);
        items[nextLast] = item;
        nextLast = (nextLast + 1) % capacity;
        ++size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty())
            return null;
        if(needShrink())
            resize(capacity / RESIZE_FACTOR);
        nextFirst = (nextFirst + 1) % capacity;
        T elem = items[nextFirst];
        items[nextFirst] = null;
        --size;
        return elem;
    }

    @Override
    public T removeLast() {
        if(isEmpty())
            return null;
        if(needShrink())
            resize(capacity / RESIZE_FACTOR);
        nextLast = (nextLast == 0) ? (capacity - 1) : nextLast - 1;
        T elem = items[nextLast];
        items[nextLast] = null;
        --size;
        return elem;
    }

    @Override
    public T get(int index) {
        if(index < 0 || size() <= index)
            return null;
        else
            return items[(nextFirst + 1 + index) % capacity];
    }

    @Override
    public void printDeque() {
        for(int curr = (nextFirst + 1) % capacity; curr != nextLast; curr = (curr + 1) % capacity) {
            System.out.print(items[curr]);
            System.out.print(' '); //TODO: Should I strip the last space?
        }
        System.out.print('\n');
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T item = get(pos);
            ++pos;
            return item;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

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
//        Iterator<T> iter1 = iterator();
//        Iterator<T> iter2 = other.iterator();
//        while(iter1.hasNext()) {
//            T curr1 = iter1.next();
//            T curr2 = iter2.next();
//            if(!curr1.equals(curr2))
//                return false;
//        }
        for(int i = 0; i < size(); ++i) {
            if(!get(i).equals(other.get(i)))
                return false;
        }
        return true;
    }
}
