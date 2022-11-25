package sets;

import java.util.Objects;

public class Entry<K> {

    private K key;
    private Entry<K> next;

    public Entry(K key) {
        this(key, null);
    }

    public Entry(K key, Entry<K> next){
        this.key = key;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Entry getNext() {
        return next;
    }

    public void setNext(Entry<K> next) {
        this.next = next;
    }
}
