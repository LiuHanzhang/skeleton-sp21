package deque;

public class ArrayDeque<T> {
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

    public boolean isEmpty() {
        return size == 0;
    }

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

    public void addFirst(T item) {
        if(full())
            resize(capacity * RESIZE_FACTOR);
        items[nextFirst] = item;
        nextFirst = (nextFirst == 0) ? (capacity - 1) : nextFirst - 1;
        ++size;
    }

    public void addLast(T item) {
        if(full())
            resize(capacity * RESIZE_FACTOR);
        items[nextLast] = item;
        nextLast = (nextLast + 1) % capacity;
        ++size;
    }

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

    public T get(int index) {
        if(index < 0 || size() <= index)
            return null;
        else
            return items[(nextFirst + 1 + index) % capacity];
    }

    public void printDeque() {
        for(int curr = (nextFirst + 1) % capacity; curr != nextLast; curr = (curr + 1) % capacity) {
            System.out.print(items[curr]);
            System.out.print(' '); //TODO: Should I strip the last space?
        }
        System.out.print('\n');
    }
}
