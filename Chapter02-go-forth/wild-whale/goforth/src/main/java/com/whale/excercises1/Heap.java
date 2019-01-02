package com.whale.excercises1;

import java.util.HashMap;
import java.util.Map;

public class Heap {

    private Map<String, Object> heap;

    public Heap() {
        this.heap = new HashMap<>();
    }

    /**
     * return value about key
     * @param key
     * @return
     */
    public Object get(String key) {
        return this.heap.get(key);
    }

    /**
     * insert value about key
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        this.heap.put(key, value);
    }

    /**
     * delete value about key
     * @param key
     */
    public void del(String key) {
        this.heap.remove(key);
    }
}
