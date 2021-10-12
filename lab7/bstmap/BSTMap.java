package bstmap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    @Override
    public Iterator<K> iterator() { throw new UnsupportedOperationException(); }

    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left;
        private BSTNode right;
        public BSTNode(K k, V v, BSTNode l, BSTNode r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }
    }

    private BSTNode root = null;
    private BSTNode hot = null; // The parent of hit node; Idea from Deng's lecture
    private int size = 0;

    /* Search and return the node with key k, hot is the parent of the searched node
    *  If not found, return null, hot is the last failed node */
    private BSTNode search(K k) {
        if (root == null || k.compareTo(root.key) == 0) {
            hot = null;
            return root;
        }
        hot = root;
        while (true) {
            BSTNode child = k.compareTo(hot.key) < 0 ? hot.left : hot.right;
            if (child == null || k.compareTo(child.key) == 0)
                return child;
            hot = child;
        }
    }

    @Override
    public V get(K key) {
        BSTNode node = search(key);
        return node == null ? null : node.val;
    }

    @Override
    public void put(K key, V value) {
        if(search(key) == null) { // If this key doesn't already exist
            BSTNode newNode = new BSTNode(key, value, null, null);
            if(hot == null)
                root = newNode;
            else if(key.compareTo(hot.key) < 0)
                hot.left = newNode;
            else
                hot.right = newNode;
            ++size;
        }
    }

    @Override
    public Set<K> keySet() { throw new UnsupportedOperationException(); }

    @Override
    public V remove(K key) { throw new UnsupportedOperationException(); }

    @Override
    public V remove(K key, V value) { throw new UnsupportedOperationException(); }

    @Override
    public boolean containsKey(K key) {
        return search(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null; // TODO: Will garbage collector do all the cleaning job for me?
    }
}
