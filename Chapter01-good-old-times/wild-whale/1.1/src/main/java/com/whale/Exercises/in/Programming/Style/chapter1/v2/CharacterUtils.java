package com.whale.Exercises.in.Programming.Style.chapter1.v2;

public class CharacterUtils {
    public static boolean isAlphaNum(char c) {
        if (Character.isAlphabetic(c) || Character.isDigit(c)) {
            return true;
        }
        return false;
    }
}
