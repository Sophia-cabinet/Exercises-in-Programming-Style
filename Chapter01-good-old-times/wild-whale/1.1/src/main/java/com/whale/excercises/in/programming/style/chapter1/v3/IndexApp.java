package com.whale.excercises.in.programming.style.chapter1.v3;

import com.whale.Exercises.in.Programming.Style.chapter1.v2.StopWordManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IndexApp {
    // File read by line
    // 45 line per one page
    // term dms 100 page 만 기록
    // file.index 파일에 텀 & 페이지 숫자 기록 100 개까지
    private static final String STOP_WORD_FILE_PATH = "../../../stop_words.txt";
    private static final String INDEX_FILE_PATH = "../../../term.index";

    public static void main(String[] args) {

        String filePath = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))  {
            StopWordManager stopWordManager = new StopWordManager(STOP_WORD_FILE_PATH, ",");
//            IndexFileManager indexManager = new IndexFileManager(INDEX_FILE_PATH, "rw");
            IndexMemoryManager indexManager = new IndexMemoryManager();       // Memory 사용

            Indexer indexer = new Indexer.Builder()
                    .setIndexFileManager(indexManager)
                    .setStopWordManager(stopWordManager)
                    .setLinesPerPage(45)
//                    .setMaxIndexCount(100)
                    .build();
            indexer.index(br);
            indexer.print();

            indexer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
