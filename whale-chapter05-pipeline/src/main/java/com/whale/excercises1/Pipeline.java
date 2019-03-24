package com.whale.excercises1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pipeline {

    public static List<Character> readFile(String path2File) throws IOException {
        List<Character> data = new ArrayList<>();
        for (String line : FileUtils.readLines(new File(path2File), "UTF-8")) {
            line = line + "\n";
            Collections.addAll(data, line.chars().mapToObj(c -> (char)c).toArray(Character[]::new));
        }
        return data;
    }

    private static boolean isAlnum(char c) {
        return (Character.isAlphabetic(c) || Character.isDigit(c));
    }

    public static List<Character> filterCharsAndNormalize(List<Character> data) {
        for (int i=0 ; i<data.size() ; i++) {
            if (!isAlnum(data.get(i))) {
                data.set(i, ' ');
            } else {
                data.set(i, Character.toLowerCase(data.get(i)));
            }
        }
        return data;
    }

    public static List<String> scan(List<Character> data) {
        List<String> words = new ArrayList<>();
        String dataString = data.stream().map(e->e.toString()).collect(Collectors.joining());
        Collections.addAll(words, Stream.of(dataString.split(" ")).filter(w -> !w.isEmpty()).toArray(String[]::new));
        return words;
    }

    public static List<String> removeStopWords(List<String> words) throws IOException {
        String stopWordFilepath = "stop_words.txt";
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
        return words;
    }

    public static Map<String, Integer> frequencies(List<String> words) {
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String w : words) {
            if (wordFreq.containsKey(w)) {
                wordFreq.put(w, wordFreq.get(w) + 1);
            } else {
                wordFreq.put(w, 1);
            }
        }
        return wordFreq;
    }

    public static Map<String, Integer> sort(Map<String, Integer> wordFreq) {
        wordFreq = wordFreq.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return wordFreq;
    }

    public static void printAll(Map<String, Integer> wordFreqs, int count) {
        Iterator<String> keys = wordFreqs.keySet().iterator();
        if (count>0 && keys.hasNext()) {
            String key = keys.next();
            System.out.println(String.format("%s - %s", key, wordFreqs.get(key)));
            wordFreqs.remove(key);
            printAll(wordFreqs, --count);
        }
    }

    public static void main(String[] args) throws IOException {

        printAll(sort(frequencies(removeStopWords(scan(filterCharsAndNormalize(readFile(args[0])))))), 25);
    }
}
