package com.whale.excercises1;

public class CharacterUtils {
    public static boolean isAlphaNum(char c) {
        if (Character.isAlphabetic(c) || Character.isDigit(c)) {
            return true;
        }
        return false;
    }
}
