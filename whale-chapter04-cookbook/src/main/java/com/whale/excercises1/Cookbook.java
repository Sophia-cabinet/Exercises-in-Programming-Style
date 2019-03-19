package com.whale.excercises1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cookbook {
    private List<Character> data;
    private List<String> words;
    private Map<String, Integer> wordFreq;

    public Cookbook() {
        this.data = new ArrayList<>();
        this.words = new ArrayList<>();
        this.wordFreq = new HashMap<>();
    }

    public static void main(String[] args) throws IOException {
        Cookbook cookbook = new Cookbook();
        cookbook.readFile(args[0]);
        cookbook.filterCharsAndNormalize();
        cookbook.scan();
        cookbook.removeStopWords();
        cookbook.frequencies();
        cookbook.sort();
        cookbook.print();
    }

    public void readFile(String filePath) throws IOException {
        for (String line : FileUtils.readLines(new File(filePath), "UTF-8")) {
            line = line + "\n";
//            Character[] tmp = ArrayUtils.toObject(line.toCharArray());
//            Collections.addAll(data, tmp);
            Collections.addAll(data, line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
        }
    }

    private boolean isAlnum(char c) {
        return (Character.isAlphabetic(c) || Character.isDigit(c));
    }

    public void filterCharsAndNormalize() {
        for (int i=0 ; i<data.size() ; i++) {
            if (!isAlnum(data.get(i))) {
                data.set(i, ' ');
            } else {
                data.set(i, Character.toLowerCase(data.get(i)));
            }
        }
    }

    public void scan() {
//        String dataString = data.stream().map(Object::toString).collect(Collectors.joining(""));
        String dataString = data.stream().map(e->e.toString()).collect(Collectors.joining());
        Collections.addAll(words, Stream.of(dataString.split(" ")).filter(w -> !w.isEmpty()).toArray(String[]::new));
    }

    public void removeStopWords() throws IOException {
        String stopWordFilepath = "/Users/loading/Develop/study/Exercises-in-Programming-Style/stop_words.txt";
        String fileContents = FileUtils.readFileToString(new File(stopWordFilepath), "UTF-8");

        List<String> stopWords = Arrays.stream(fileContents.split(","))
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());
        for (char ch = 'a'; ch <= 'z'; ch++)
            stopWords.add(String.valueOf(ch));
        List<Integer> indexes = new ArrayList<>();

        for (int i=0 ; i<words.size() ; i++) {
            if (stopWords.contains(words.get(i))) {
                indexes.add(i);
            }
        }
        Collections.reverse(indexes);
        for (int i : indexes) {
            words.remove(i);
        }
    }

    public void frequencies() {
        for (String w : words) {
            if (wordFreq.containsKey(w)) {
                wordFreq.put(w, wordFreq.get(w) + 1);
            } else {
                wordFreq.put(w, 1);
            }
        }
    }

    public void sort() {
        wordFreq = wordFreq.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void print() {
        wordFreq.entrySet().stream()
//                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(25)
                .forEach(System.out::println); // or any other terminal method

    }
}
