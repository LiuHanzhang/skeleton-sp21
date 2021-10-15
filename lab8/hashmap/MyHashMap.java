package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.lang.Math;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int capacity;   // #buckets
    private int size;       // #elements
    private final double loadFactor;
    private final int resizeFactor = 2;

    /** Constructors */
    public MyHashMap() {
        size = 0;
        capacity = 16;
        loadFactor = 0.75;
        buckets = createTable(capacity);
    }

    public MyHashMap(int initialSize) {
        size = 0;
        capacity = initialSize;
        loadFactor = 0.75;
        buckets = createTable(capacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        capacity = initialSize;
        loadFactor = maxLoad;
        buckets = createTable(capacity);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for(int i = 0; i < tableSize; ++i) {
            table[i] = createBucket();
        }
        return table;
    }

    private void resize(int newCapacity) {
        int oldCapcity = capacity;
        capacity = newCapacity;
        Collection<Node>[] oldBuckets = buckets;
        buckets = createTable(newCapacity);
        for(int i = 0; i < oldCapcity; ++i) {
            if(oldBuckets[i] == null)
                continue;
            for(Node n : oldBuckets[i])
                putForResize(n.key, n.value);
        }
        // TODO: Will garbage collector delete all memory oldBuckets refer to for me?
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        capacity = 16;
        buckets = createTable(capacity);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketNo = Math.floorMod(key.hashCode(), capacity);
        if(buckets[bucketNo] == null)
            return false;
        for(Node n : buckets[bucketNo]) {
            if (n.key.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        int bucketNo = Math.floorMod(key.hashCode(), capacity);
        if(buckets[bucketNo] == null)
            return null;
        for(Node n : buckets[bucketNo]) {
            if (n.key.equals(key))
                return n.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    /* Return true if update */
    private boolean putForResize(K key, V value) {
        int bucketNo = Math.floorMod(key.hashCode(), capacity);
        if(buckets[bucketNo] == null)
            buckets[bucketNo] = createBucket();
        for(Node n : buckets[bucketNo]) {
            if(n.key.equals(key)) {
                n.value = value;
                return true;
            }
        }
        Node e = createNode(key, value);
        buckets[bucketNo].add(e);
        return false;
    }

    @Override
    public void put(K key, V value) {
        boolean update = putForResize(key, value);
        if(!update)
            ++size;
        if((double)size / capacity > loadFactor) {
            resize(capacity * resizeFactor);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new HashSet<K>();
        for(K k : this)
            s.add(k);
        return s;
    }

    @Override
    public V remove(K key) { throw new UnsupportedOperationException(); }

    @Override
    public V remove(K key, V value) { throw new UnsupportedOperationException(); }

    private class MyHashMapIterator implements Iterator<K> {
        private int bucketNo = 0;
        private Iterator<Node> currIter = buckets[bucketNo].iterator();

        @Override
        public boolean hasNext() {
            if(currIter.hasNext())
                return true;
            ++bucketNo;
            while(bucketNo < capacity) {
                currIter = buckets[bucketNo].iterator();
                if(currIter.hasNext())
                    return true;
                ++bucketNo;
            }
            return false;
        }

        @Override
        public K next() {
            if(currIter.hasNext())
                return currIter.next().key;
            ++bucketNo;
            while(bucketNo < capacity) {
                currIter = buckets[bucketNo].iterator();
                if(currIter.hasNext())
                    return currIter.next().key;
                ++bucketNo;
            }
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }
}
