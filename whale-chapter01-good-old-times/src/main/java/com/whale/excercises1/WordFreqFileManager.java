package com.whale.excercises1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class WordFreqFileManager {

    private RandomAccessFile wordFreqs;
    private List<Data> TFList;

    public WordFreqFileManager(String filePath, String mode) throws FileNotFoundException {
        touchOpen(filePath, mode);
    }

    private void touchOpen(String filePath, String mode) throws FileNotFoundException {
        try {
            FileUtils.forceDeleteOnExit(new File(filePath));
            FileUtils.write(new File(filePath), " ", "UTF-8");
        } catch (IOException e) {
            System.out.println("pass");
        }
        this.wordFreqs = new RandomAccessFile(new File(filePath), mode);
    }

    public void load(String filePath, String mode) throws FileNotFoundException {
        this.wordFreqs = new RandomAccessFile(new File(filePath), mode);
    }

    public void record(String word) throws IOException {
        String wordFreq = null;
        boolean isExistWord = false;
        int wordFreqNum = 1;

        while ((wordFreq=wordFreqs.readLine()) != null) {
            if (StringUtils.isEmpty(wordFreq.trim())) {
                break;
            }
            String[] wordFreqInfo = wordFreq.trim().split(",");
            if (StringUtils.equals(word, wordFreqInfo[0])) {
                wordFreqNum = (Integer.parseInt(wordFreqInfo[1]) + 1);
                isExistWord = true;
                break;
            }
        }
        if (!isExistWord) {
            wordFreqs.write(String.format("%20s,%04d\n", word, wordFreqNum).getBytes("UTF-8"));
        } else {
            wordFreqs.seek(wordFreqs.getFilePointer()-26);
            wordFreqs.write(String.format("%20s,%04d\n", word, wordFreqNum).getBytes("UTF-8"));
        }
        wordFreqs.seek(0);
    }

    public void order(int limit) throws IOException {

        TFList = new LinkedList<>();

        String wordFreq = null;
        while ((wordFreq=wordFreqs.readLine()) != null) {

            String[] freqInfo = wordFreq.split(",");
            Data newTerm = new Data(freqInfo[0], Integer.parseInt(freqInfo[1]));

            int insertPos = 0;
            for (Data term : TFList) {
                if (newTerm.getFreq()>term.getFreq()) {
                    break;
                }
                insertPos++;
            }
            TFList.add(insertPos, newTerm);

            if (TFList.size()>limit) {
                TFList.remove(limit);
            }
        }
    }

    public List<Data> getTFList() {
        return this.TFList;
    }

    public void printOrderList() {
        if (this.TFList != null) {
            for (Data term : TFList) {
                System.out.println(term.toString());
            }
        }
    }

    public void close() throws IOException {
        if (wordFreqs!=null) {
            this.wordFreqs.close();
        }
    }
}
