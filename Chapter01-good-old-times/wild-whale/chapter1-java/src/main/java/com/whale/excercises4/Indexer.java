package com.whale.excercises4;

import com.whale.excercises1.StopWordManager;
import com.whale.excercises1.StringTokenizer;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class Indexer {

    private int linesPerPage;
//    private int maxIndexCount;
    private StopWordManager stopWordManager;
    private IndexManager indexManager;

    public void setLinesPerPage(int linesPerPage) {
        this.linesPerPage = linesPerPage;
    }

//    public void setMaxIndexCount(int maxIndexCount) {
//        this.maxIndexCount = maxIndexCount;
//    }

    public void setStopWordManager(StopWordManager stopWordManager) {
        this.stopWordManager = stopWordManager;
    }

    public void setIndexManager(IndexManager indexManager) {
        this.indexManager = indexManager;
    }

    public void index(BufferedReader reader) throws IOException {

        String line = null;
        int lineNum = 0;
        while ((line=reader.readLine()) != null) {
            lineNum++;
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken().toLowerCase().trim();
                if (token.length() >= 2 && !stopWordManager.contains(token)) {
                    indexManager.record(token.trim(), getCurrentPageNum(lineNum));
                }
            }
            System.out.println(lineNum + " : " + line);
        }
    }

    public void print() throws IOException {
        this.indexManager.print();
    }

    private int getCurrentPageNum(int lineNum) {
        return (lineNum / linesPerPage) + 1;
    }

    public void close() {
        try {
            indexManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private int linesPerPage;
        private int maxIndexCount;
        private StopWordManager stopWordManager;
        private IndexManager indexManager;

        public Builder setLinesPerPage(int linesPerPage) {
            this.linesPerPage = linesPerPage;
            return this;
        }

        public Builder setMaxIndexCount(int maxIndexCount) {
            this.maxIndexCount = maxIndexCount;
            return this;
        }

        public Builder setStopWordManager(StopWordManager stopWordManager) {
            this.stopWordManager = stopWordManager;
            return this;
        }

        public Builder setIndexFileManager(IndexManager indexManager) {
            this.indexManager = indexManager;
            return this;
        }

        public Indexer build() {
            Indexer indexer = new Indexer();
            indexer.setIndexManager(this.indexManager);
            indexer.setStopWordManager(this.stopWordManager);
            indexer.setLinesPerPage(this.linesPerPage);
//            indexer.setMaxIndexCount(this.maxIndexCount);
            return indexer;
        }
    }
}
