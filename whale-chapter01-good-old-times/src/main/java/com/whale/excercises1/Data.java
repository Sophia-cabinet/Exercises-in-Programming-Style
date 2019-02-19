package com.whale.excercises1;

public class Data {
    private String word;
    private int freq;

    public Data(String word, int freq) {
        this.word = word;
        this.freq = freq;
    }

    public String getWord() {
        return this.word;
    }

    public int getFreq() {
        return this.freq;
    }

    @Override
    public String toString() {
        return String.format("%20s,%04d", word, freq);
    }
}
