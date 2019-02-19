package com.whale.excercises4;

import java.io.IOException;
import java.util.*;

public class IndexMemoryManager implements IndexManager{

    private Map<String, Index> indice;

    public IndexMemoryManager() {
        this.indice = new TreeMap<>();
    }

    @Override
    public void record(String term, int pageNum) throws IOException {

        if (!this.indice.containsKey(term)) {
            Index termIndex = new Index(term, String.valueOf(pageNum));
            this.indice.put(term, termIndex);
        } else {
            Index termIndex = this.indice.get(term);
            if (termIndex.getPageNums().size()<100) {
                termIndex.getPageNums().add(String.valueOf(pageNum));
            }
        }
    }

    @Override
    public void print() {
        Iterator<String> iteratorKey = this.indice.keySet( ).iterator( );
        while (iteratorKey.hasNext()) {
            String term = iteratorKey.next();
            Index termIndex = this.indice.get(term);
            System.out.println(termIndex.toString());
        }
    }

    @Override
    public void close() {

    }
}
