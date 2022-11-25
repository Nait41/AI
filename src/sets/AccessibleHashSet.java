package sets;

import java.io.Serializable;
import java.util.*;

public class AccessibleHashSet<K> extends AbstractSet<K> implements Set<K>, Cloneable, Serializable {

    static private final int DEFAULT_CAPACITY = 16;
    static private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry<K>[] buckets;
    private int size;
    private int modCount;
    private int threshold;

    public AccessibleHashSet() {
        this(DEFAULT_CAPACITY);
    }

    public AccessibleHashSet(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        buckets = (Entry<K>[]) new Entry[capacity];
        threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
    }

    public K get(Object key) {
        int idx = hash(key);
        Entry<K> e = buckets[idx];
        while (e != null) {
            if (key.equals(e.getKey()))
                return e.getKey();
            e = e.getNext();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object key) {
        int idx = hash(key);
        Entry<K> e = buckets[idx];
        while (e != null) {
            if (key.equals(e.getKey()))
                return true;
            e = e.getNext();
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new AccessibleHashIterator<K>();
    }

    @Override
    public boolean add(K key) {
        int idx = hash(key);
        Entry<K> e = buckets[idx];

        while (e != null) {
            if (key.equals(e.getKey()))
                return false;
            e = e.getNext();
        }

        modCount++;
        if (++size > threshold) {
            rehash();
            idx = hash(key);
        }

        addEntry(key, idx, true);
        return true;
    }

    @Override
    public boolean remove(Object key) {
        int idx = hash(key);
        Entry<K> e = buckets[idx];
        Entry<K> last = null;

        while (e != null) {
            if (key.equals(e.getKey())) {
                modCount++;
                if (last == null)
                    buckets[idx] = e.getNext();
                else
                    last.setNext(e.getNext());
                size--;
                return true;
            }
            last = e;
            e = e.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        if (size != 0) {
            modCount++;
            Arrays.fill(buckets, null);
            size = 0;
        }
    }

    private int hash(Object key){
        return key == null ? 0 : Math.abs(key.hashCode() % buckets.length);
    }

    void addEntry(K key, int idx, boolean callRemove) {
        Entry<K> e = new Entry<>(key, buckets[idx]);
        buckets[idx] = e;
    }

    private void rehash() {
        Entry<K>[] oldBuckets = buckets;

        int newCapacity = (buckets.length * 2) + 1;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
        buckets = (Entry<K>[]) new Entry[newCapacity];

        for (int i = oldBuckets.length - 1; i >= 0; i--) {
            Entry<K> e = oldBuckets[i];
            while (e != null) {
                int idx = hash(e.getKey());
                Entry<K> dest = buckets[idx];
                Entry<K> next = e.getNext();
                e.setNext(buckets[idx]);
                buckets[idx] = e;
                e = next;
            }
        }
    }

    private final class AccessibleHashIterator<K> implements Iterator<K> {

        private int knownMod = modCount;
        private int count = size;
        private int idx = buckets.length;
        private Entry<K> last;
        private Entry<K> next;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public K next() {
            if (knownMod != modCount)
                throw new ConcurrentModificationException();
            if (count == 0)
                throw new NoSuchElementException();
            count--;
            Entry<K> e = next;

            while (e == null)
                e = (Entry<K>) buckets[--idx];

            next = e.getNext();
            last = e;

            return e.getKey();
        }
    }
}


