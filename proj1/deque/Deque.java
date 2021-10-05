package deque;

import java.util.Iterator;

public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    default public boolean isEmpty() { return size() == 0; }
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);

    // NOTE: This interface is not declared in autograder
    //public Iterator<T> iterator();
    // NOTE: https://stackoverflow.com/questions/24016962/java8-why-is-it-forbidden-to-define-a-default-method-for-a-method-from-java-lan/24026292
    // @Override
    // default public boolean equals(Object o) {...}
}
