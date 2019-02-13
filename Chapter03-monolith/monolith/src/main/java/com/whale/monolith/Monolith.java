package com.whale.monolith;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Monolith {

    public static void main(String[] args) {
        try {
            Monolith monolith = new Monolith();
            monolith.start(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void start(String docFilePath) throws IOException {

        List<Object> wordFreqs = new ArrayList<>();
        String stopWordsString = FileUtils.readFileToString(new File("/Users/loading/Develop/study/Exercises-in-Programming-Style/stop_words.txt"), "UTF-8");
        List<String> stopWords = new ArrayList<>(Arrays.asList(stopWordsString.split(",")));
        for (char ch = 'a'; ch <= 'z'; ch++)
            stopWords.add(String.valueOf(ch));

        for (String line : FileUtils.readLines(new File(docFilePath), "UTF-8")) {
            int i = 0;
            Integer startChar = null;
            line = line + "\n";
            for (Character c : line.toCharArray()) {
                if(startChar==null) {
                    if (isAlnum(c)) {
                        startChar = i;
                    }
                } else {
                    if (!isAlnum(c)) {
                        boolean found = false;
                        String word = line.substring(startChar, i).toLowerCase();
                        if (!stopWords.contains(word)) {
                            int pairIndex = 0;
                            for (Object pair : wordFreqs) {
                                String term = (String)((Object[])pair)[0];
                                if (word.equals(term.trim())) {
                                    Integer count = ((Integer)((Object[])pair)[1]);
                                    ((Object[])pair)[1] = count + 1;
                                    found = true;
                                    break;
                                }
                                pairIndex++;
                            }
                            if (!found) {
                                Object[] f = new Object[2];
                                f[0] = word;
                                f[1] = 1;
                                wordFreqs.add(f);
                            } else if (wordFreqs.size()>1) {

                                for (int startIndex=pairIndex-1 ; pairIndex>0 && startIndex>=0 ; startIndex--) {
                                    Object[] pairObject = ((Object[])wordFreqs.get(pairIndex));
                                    Object[] currentObject = ((Object[])wordFreqs.get(startIndex));

                                    int leftValue = ((Integer)(pairObject)[1]);
                                    int rightValue = ((Integer)(currentObject[1]));
                                    if (leftValue > rightValue) {
                                        wordFreqs.set(pairIndex, currentObject);
                                        wordFreqs.set(startIndex, pairObject);
                                        pairIndex = startIndex;
                                    }
                                }
                            }
                        }
                        startChar = null;
                    }
                }
                i += 1;
            }
        }
        for (int i=0 ; i<25 ; i++) {
            Object[] tf = ((Object[])wordFreqs.get(i));
            System.out.println((String)tf[0] + " - " + (Integer)tf[1]);
        }
    }

    private boolean isAlnum(char c) {

        if ((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9')) {
            return true;
        }
        return false;
    }
}
