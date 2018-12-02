package com.whale.Exercises.in.Programming.Style.chapter1.v2;

import java.io.*;
import java.util.List;

public class GoodOldTimes {

    private static final String STOP_WORD_FILE_PATH = "../../../stop_words.txt";
    private static final String WORD_FREQ_FILE_PATH = "../../../word_freqs";

    public static void main (String[] args) {

        String filePath = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))  {
            StopWordManager stopWordManager = new StopWordManager(STOP_WORD_FILE_PATH, ",");
            WordFreqFileManager wordFreqFileManager = new WordFreqFileManager(WORD_FREQ_FILE_PATH , "rw");

            TFGenerator tf = new TFGenerator.Builder()
                                            .setTermNum(25)
                                            .setStopWordManager(stopWordManager)
                                            .setWordFreqFileManager(wordFreqFileManager)
                                            .build();
            List<Data> tfList = tf.generate(br);

            for (Data term : tfList) {
                System.out.println(term.toString());
            }

            tf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
