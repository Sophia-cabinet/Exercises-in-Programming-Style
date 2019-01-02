package com.whale.excercises1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class GoForth {

    private Stack stack;
    private Heap heap;

    public static void main(String[] args) {

        try {
            GoForth goForth = new GoForth();
            goForth.start(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GoForth() {
        this.stack = new Stack();
        this.heap = new Heap();
    }

    public void start(String filePath) throws Exception {
        this.stack.push(filePath);
        readFile();
        filterChars();
        scan();
        removeStopWords();
        frequencies();
        sort();

        this.stack.push(0);

        while (((Integer)this.stack.currentValue()<25) && this.stack.len()>1) {
            this.heap.put("i", this.stack.pop());
            String wordFreq = (String)this.stack.pop();
            System.out.println(wordFreq);
            this.stack.push(heap.get("i"));
            this.stack.push(1);
            this.stack.push((Integer)this.stack.pop() + (Integer)this.stack.pop());
        }
    }

    public void readFile() throws Exception {

        String line = FileUtils.readFileToString(new File((String)this.stack.pop()), "UTF-8");
        this.stack.push(line);
    }

    public void filterChars() throws Exception {

        this.stack.push(Pattern.compile("[\\W_]+"));
        this.stack.push(((Pattern)this.stack.pop()).matcher((String)this.stack.pop()).replaceAll(" ").toLowerCase());
    }

    public void scan() throws Exception {

        this.stack.push(new ArrayList<>(Arrays.asList(((String)this.stack.pop()).split(" "))));
    }

    public void removeStopWords() throws Exception {

        File stopWordFile = new File("../../../stop_words.txt");
        this.stack.pushList(new ArrayList<>(Arrays.asList(FileUtils.readFileToString(stopWordFile, "UTF-8").split(","))));
        this.stack.extend(asciiLowercase());

        this.heap.put("stop_words", this.stack.pop());
        this.heap.put("words", new ArrayList<String>());

        while (this.stack.len()>0) {
            if (((List<String>)heap.get("stop_words")).contains((String)this.stack.currentValue())) {
                this.stack.pop();
            } else {
                ((List<String>)heap.get("words")).add((String)this.stack.pop());
            }
        }
        this.stack.push((List<String>)heap.get("words"));
        this.heap.del("stop_words");
        this.heap.del("words");
    }

    public List<String> asciiLowercase() {
        List<String> tmp = new ArrayList<String>();
        for (char ch = 'a'; ch <= 'z'; ch++)
            tmp.add(String.valueOf(ch));
        return tmp;
    }

    public void frequencies() throws Exception {

        this.heap.put("word_freqs", new HashMap<String, Integer>());
        while (this.stack.len()>0) {
            if (((Map<String, Integer>)heap.get("word_freqs")).containsKey((String)this.stack.currentValue())) {
                // int
                this.stack.push(((Map<String, Integer>)heap.get("word_freqs")).get((String)this.stack.currentValue()));
                this.stack.push(1);
                this.stack.push((Integer)this.stack.pop() + (Integer)this.stack.pop());
            } else {
                this.stack.push(1);
            }
            Integer value = (Integer)this.stack.pop();
            String key = (String)this.stack.pop();
            ((Map<String, Integer>)heap.get("word_freqs")).put(key, value);
        }
        this.stack.push(this.heap.get("word_freqs"));
        this.heap.del("word_freqs");
    }

    public void sort() throws Exception {
        Map<String, Integer> map = (Map<String, Integer>)this.stack.pop();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

//        Map<String, Integer> result = new LinkedHashMap<>();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.add(String.format("%s, %d",entry.getKey(), entry.getValue()));
        }

        this.stack.push(result);
    }
}
