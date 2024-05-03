package com.example.myapplication.tokenizer;

public class Token {
    public enum Type {NAME, KEYWORD, INT, COMPARISON}

    static final String[] keyword = {"name", "capacity", "rating"};
    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public static boolean containsCapacity(String input) {
        return input.contains(keyword[1]);
    }

    public static boolean containsRating(String input) {
        return input.contains(keyword[2]);
    }

    public static boolean containsName(String input) {
        return input.contains(keyword[0]);
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

}
