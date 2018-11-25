package com.whale.Exercises.in.Programming.Style.chapter1.v2;

public class StringTokenizer {

    private char[] str;
    private int pos;

    public StringTokenizer(String str) {
        this.str = str.toCharArray();
        this.pos = 0;
    }

    public String nextToken() {
        StringBuilder sb = new StringBuilder();
        for (; pos<str.length; pos++) {
            if (!CharacterUtils.isAlphaNum(str[pos])) {
                if (sb.length()==0) {
                    continue;
                } else {
                    break;
                }
            }
            sb.append(str[pos]);
        }
        return sb.toString();
    }

    public boolean hasMoreTokens() {
        if (this.str==null || this.str.length<=this.pos) {
            return false;
        }
        return true;
    }
}
