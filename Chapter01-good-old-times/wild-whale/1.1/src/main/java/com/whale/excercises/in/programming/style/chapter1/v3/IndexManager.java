package com.whale.excercises.in.programming.style.chapter1.v3;

import java.io.IOException;

public interface IndexManager {
    void record(String term, int pageNum) throws IOException;
    void print() throws IOException;
    void close() throws IOException;
}
