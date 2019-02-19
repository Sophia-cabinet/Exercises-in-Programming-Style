package com.whale.excercises4;

import java.io.IOException;

public interface IndexManager {
    void record(String term, int pageNum) throws IOException;
    void print() throws IOException;
    void close() throws IOException;
}
