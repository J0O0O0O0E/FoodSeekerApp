package com.example.myapplication.tokenizer;

public class Token {
    private String token;
    private Type type;

    public enum Type {
        KEYWORD, COMPARISON, INT, INVALID
    }

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Token[%s, %s]", token, type);
    }
}