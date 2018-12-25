package com.whale.exercises4;

import com.whale.excercises1.Heap;
import com.whale.excercises1.Stack;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Indexer {

    private static final int LINE_PER_PAGE = 45;
    private static final int MAX_INDEX_NUM = 100;

    private Stack stack;
    private Heap heap;

    public static void main(String[] args) {

        try {
            Indexer indexer = new Indexer();
            indexer.start(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Indexer() {
        this.stack = new Stack();
        this.heap = new Heap();
    }

    public void start(String filePath) throws Exception {

        this.heap.put("index", new TreeMap<String, Set<Integer>>());

        this.stack.push(filePath);
        readFile();

        Iterator<String> pageIter = ((List<String>) this.heap.get("pages")).iterator();
        int pageNum = 1;
        while (pageIter.hasNext()) {
            this.stack.push(pageIter.next());
            filterChars();
            scan();
            removeStopWords();
            this.stack.push(pageNum);
            index();
            pageNum++;
        }

        this.stack.push(this.heap.get("index"));
        this.heap.del("index");
        convertString();

        this.stack.push(0);
        while (((Integer) this.stack.currentValue() < 25) && this.stack.len() > 1) {
            this.heap.put("i", this.stack.pop());
            String wordFreq = (String) this.stack.pop();
            System.out.println(wordFreq);
            this.stack.push(heap.get("i"));
            this.stack.push(1);
            this.stack.push((Integer) this.stack.pop() + (Integer) this.stack.pop());
        }
    }

    public void convertString() throws Exception {
        TreeMap<String, Set<Integer>> index = (TreeMap<String, Set<Integer>>)this.stack.pop();
        List<String> result = new ArrayList<String>();
        Iterator<String> keys = index.descendingKeySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            result.add(String.format("%s - %s", key, index.get(key).toString()));
        }
        this.stack.push(result);
    }

    public void index() throws Exception {

        int pageNum = (int) this.stack.pop();
        while (this.stack.len() > 0) {
            if (((Map<String, Set<Integer>>) heap.get("index")).containsKey((String) this.stack.currentValue())) {
                String term = (String) this.stack.pop();
                if (((Map<String, Set<Integer>>) heap.get("index")).get(term).size() < MAX_INDEX_NUM) {
                    ((Map<String, Set<Integer>>) heap.get("index")).get(term).add(pageNum);
                }
            } else {
                Set<Integer> pageNums = Stream.of(pageNum).collect(Collectors.toCollection(HashSet::new));
                ((Map<String, Set<Integer>>) heap.get("index")).put((String) this.stack.pop(), pageNums);
            }
        }
    }

    public void readFile() throws Exception {

        List<String> lines = FileUtils.readLines(new File((String) this.stack.pop()), "UTF-8");
        List<String> pages = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i));
            if (i != 0 && (i % LINE_PER_PAGE) == 0) {
                pages.add(sb.toString());
                sb.setLength(0);
            }
        }
        this.heap.put("pages", pages);
    }

    public void filterChars() throws Exception {

        this.stack.push(Pattern.compile("[\\W_]+"));
        this.stack.push(((Pattern) this.stack.pop()).matcher((String) this.stack.pop()).replaceAll(" ").toLowerCase());
        // 숫자 지우기 위해 추가
        this.stack.push(Pattern.compile("[\\d]+"));
        this.stack.push(((Pattern) this.stack.pop()).matcher((String) this.stack.pop()).replaceAll(" ").toLowerCase());
    }

    public void scan() throws Exception {

        this.stack.push(new ArrayList<>(Arrays.asList(((String) this.stack.pop()).split(" "))));
    }

    public void removeStopWords() throws Exception {

        File stopWordFile = new File("../../../stop_words.txt");
        this.stack.pushList(new ArrayList<>(Arrays.asList(FileUtils.readFileToString(stopWordFile, "UTF-8").split(","))));
        this.stack.extend(asciiLowercase());

        this.heap.put("stop_words", this.stack.pop());
        this.heap.put("words", new ArrayList<String>());

        while (this.stack.len() > 0) {
            if (((List<String>) heap.get("stop_words")).contains((String) this.stack.currentValue())) {
                this.stack.pop();
            } else {
                ((List<String>) heap.get("words")).add((String) this.stack.pop());
            }
        }
        this.stack.push((List<String>) heap.get("words"));
        this.heap.del("stop_words");
        this.heap.del("words");
    }

    public List<String> asciiLowercase() {
        List<String> tmp = new ArrayList<String>();
        for (char ch = 'a'; ch <= 'z'; ch++)
            tmp.add(String.valueOf(ch));
        return tmp;
    }
}
