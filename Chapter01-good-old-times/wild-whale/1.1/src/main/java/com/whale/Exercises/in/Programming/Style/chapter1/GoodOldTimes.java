package com.whale.Exercises.in.Programming.Style.chapter1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class GoodOldTimes {
    public static void main(String[] args) {
        GoodOldTimes goodOldTimes = new GoodOldTimes();
        try {
            goodOldTimes.start(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RandomAccessFile touchOpen(String fileName, String option) throws FileNotFoundException {
        try {
            FileUtils.forceDeleteOnExit(new File(fileName));
            FileUtils.write(new File(fileName), " ", "UTF-8");
        } catch (IOException e) {
            System.out.println("pass");
        }
        return new RandomAccessFile(new File(fileName), option);
    }

    public void start(String filePath) throws IOException {

        Object[] data = new Object[8];
        File file = new File("../../../stop_words.txt");
        RandomAccessFile stopWordFile = new RandomAccessFile(file, "r");
        byte[] buffer = new byte[(int)stopWordFile.length()];
        stopWordFile.readFully(buffer);
        List<String> stopWord = Arrays.asList((new String(buffer)).split(","));
        data[0] = stopWord;
        stopWordFile.close();

        data[1] = null;     // data[1]은 (최대 80자인) 줄
        data[2] = null;             // data[2]는 단어의 시작 문자 색인
        data[3] = 0;                // data[3]은 문자에 대한 색인이며 i = 0
        data[4] = false;            // data[4]는 단어를 찾았는지 여부를 나타내는 플래그
        data[5] = "";               // data[5]는 해당 단어
        data[6] = "";               // data[6]은 단어, NNNN
        data[7] = 0;                // data[7]은 빈도

        String wordFreqsPath = "/Users/loading/Develop/study/Exercises-in-Programming-Style/Chapter01-good-old-times/word_freqs";
        RandomAccessFile wordFreqs = touchOpen(wordFreqsPath, "rw");

        for (String line : FileUtils.readLines(new File(filePath), "UTF-8")) {
            data[1] = line;
            if (StringUtils.isEmpty((String) data[1])) {
                continue;
            }
            if (!StringUtils.endsWith((String) data[1], "\n")) {
                data[1] = ((String) data[1]) + "\n";
            }
            data[2] = null;
            data[3] =  0;

            // 해당 줄 내 문자를 순회
            for (char c : ((String) data[1]).toCharArray()) {
                if (data[2] == null) {
                    if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                        // 단어의 시작을 찾았다
                        data[2] = data[3];
                    }
                } else {
                    if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                        // 단어의 끝을 찾았으므로 처리한다.
                        data[4] = false;
                        data[5] = ((String)data[1]).substring((int) data[2], (int) data[3]).toLowerCase();
                        if (((String) data[5]).length() >= 2 && !((List<String>) data[0]).contains((String)data[5])) {
                            // 이미 존재하는지 확인한다.
                            String wordFreq;
                            while ((wordFreq= wordFreqs.readLine()) != null) {
                                if (StringUtils.isEmpty(wordFreq.trim())) {
                                    break;
                                }
                                data[6] = new String(wordFreq.trim().getBytes("UTF-8"), "UTF-8");
                                data[7] = Integer.parseInt(((String) data[6]).trim().split(",")[1]);
                                // 공백 문자가 없는 단어
                                data[6] = ((String) data[6]).split(",")[0].trim();
                                if (StringUtils.equals((String) data[5], (String) data[6])) {
                                    data[7] = ((int) data[7]) + 1;
                                    data[4] = true;
                                    break;
                                }
                            }
                            if (!(boolean)data[4]) {
//                              word_freqs.seek(0, 1)   # 윈도우에서는 필요하다
                                wordFreqs.write(String.format("%20s,%04d\n", (String)data[5], 1).getBytes("UTF-8"));
                            } else {
                                wordFreqs.seek(wordFreqs.getFilePointer()-26);   // 윈도우에서는 필요하다
                                wordFreqs.write(String.format("%20s,%04d\n", (String)data[5], (int)data[7]).getBytes("UTF-8"));
                            }
                            wordFreqs.seek(0);
                        }
                        data[2] = null;
                    }
                }
                data[3] = ((int) data[3]) + 1;
            }
        }
        // 입력 파일 처리를 마쳤다
        wordFreqs.close();

        // 부분 2
        // 이제 가장 자주 나온 단어 25개를 찾아야 한다
        // 메모리에 있는 이전 값은 전혀 필요 없다
        data = new Object[27];
        data[25] = "";          // data[25]는 파일에서 읽은 단어, 빈도
        data[26] = 0;           // data[26]은 빈도

        // 보조 기억 장치(파일)를 순회 한다.
        BufferedReader br = new BufferedReader(new FileReader(wordFreqsPath));
        while (true) {
            data[25] = br.readLine();
            if (StringUtils.isEmpty((String) data[25])) {
                break;
            }
            data[25] = ((String) data[25]).trim();
            data[26] = Integer.parseInt(((String)data[25]).split(",")[1]);   // 정수로 읽는다.
            data[25] = ((String)data[25]).split(",")[0];                     // 단어
            // 이 단어가 메모리에 있는 단어들보다 횟수가 더 많은지 확인한다.
            for (int i=0 ; i<25; i++) {
                if (data[i] == null || Integer.parseInt(((String)data[i]).split(",")[1]) < (int)data[26]) {
                    data[i] = (String)data[25] + "," + (int)data[26];
                    data[26] = null;
                    break;
                }
            }
        }

        for (int i=0 ; i<25 ; i++) {
            String tf = (String) data[i];
            String[] tmp = tf.split(",");
            if (tmp.length == 2) {
                System.out.println(tmp[0] + " - " + tmp[1]);
            }
        }
    }

}
