package com.whale.excercises.in.programming.style.chapter1.v3;

import java.util.Set;
import java.util.TreeSet;

public class Index {
    private String term;
    private Set<String> pageNums;

    public Index() {
        this.pageNums = new TreeSet<String>();
    }

    public Index(String term, String pageNum) {
        this();
        this.term = term;
        this.pageNums.add(pageNum);
    }

    public String getTerm() {
        return term;
    }

    public Set<String> getPageNums() {
        return pageNums;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", term, String.join(",", pageNums));
    }
}
