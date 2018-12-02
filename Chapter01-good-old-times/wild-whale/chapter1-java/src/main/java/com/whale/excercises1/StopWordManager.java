package com.whale.excercises1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWordManager {

    private List<String> stopWords = new ArrayList<>();

    public StopWordManager(String filePath, String delimiter) throws IOException {
        load(filePath, delimiter);
    }

    private void load(String pathName, String delimiter) throws IOException {
        for (String line : FileUtils.readLines(new File(pathName), "UTF-8")) {
            stopWords.addAll(Arrays.asList(line.split(delimiter)));
        }
    }

    public boolean contains(String word) {
        return stopWords.contains(word.trim());
    }
}
