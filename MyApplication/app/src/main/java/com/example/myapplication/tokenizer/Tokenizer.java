package com.example.myapplication.tokenizer;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tokenizer {
    private ArrayList<String> tokens = new ArrayList<>();

    public Tokenizer(String input) {
        tokenize(input);
    }

    private void tokenize(String input) {
        StringTokenizer st = new StringTokenizer(input, " ,.;");
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }
}
