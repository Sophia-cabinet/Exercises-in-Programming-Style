package com.whale.Exercises.in.Programming.Style.chapter1.v2;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class TFGenerator {

    private int maxTermNum;
    private StopWordManager stopWordManager;
    private WordFreqFileManager wordFreqFileManager;

    public List<Data> generate(BufferedReader reader) throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken().toLowerCase();
                if (token.length() >= 2 && !stopWordManager.contains(token)) {
                    wordFreqFileManager.record(token);
                }
            }
        }
        wordFreqFileManager.order(25);
        return wordFreqFileManager.getTFList();
    }

    public void setMaxTermNum(int maxTermNum) {
        this.maxTermNum = maxTermNum;
    }

    public void setStopWordManager(StopWordManager stopWordManager) {
        this.stopWordManager = stopWordManager;
    }

    public void setWordFreqFileManager(WordFreqFileManager wordFreqFileManager) {
        this.wordFreqFileManager = wordFreqFileManager;
    }

    public void close() {
        try {
            this.wordFreqFileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private int maxTermNum;
        private StopWordManager stopWordManager;
        private WordFreqFileManager wordFreqFileManager;

        public Builder setTermNum(int maxTermNum) {
            this.maxTermNum = maxTermNum;
            return this;
        }

        public Builder setStopWordManager(StopWordManager stopWordManager) {
            this.stopWordManager = stopWordManager;
            return this;
        }

        public Builder setWordFreqFileManager(WordFreqFileManager wordFreqFileManager) {
            this.wordFreqFileManager = wordFreqFileManager;
            return this;
        }

        public TFGenerator build() {
            TFGenerator tfGenerator = new TFGenerator();
            tfGenerator.setMaxTermNum(this.maxTermNum);
            tfGenerator.setStopWordManager(this.stopWordManager);
            tfGenerator.setWordFreqFileManager(this.wordFreqFileManager);
            return tfGenerator;
        }
    }
}
