package com.example.myapplication.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private String textBuffer;
    private Token currentToken;

    private static final ArrayList<String> validKeywords = new ArrayList<>(Arrays.asList("rating", "capacity", "distance")) ;

    public Tokenizer(String text) {
        textBuffer = text;
        next();
    }

    public Token current() {
        return currentToken;
    }

    public boolean hasNext() {
        return currentToken != null;
    }

    public void next() {
        textBuffer = textBuffer.trim();
        if (textBuffer.isEmpty()) {
            currentToken = null;
            return;
        }

        char currentChar = textBuffer.charAt(0);
        if (Character.isLetter(currentChar)) {
            int i = 1;
            while (i < textBuffer.length() && Character.isLetter(textBuffer.charAt(i))) {
                i++;
            }
            String keyword = textBuffer.substring(0, i);
            currentToken = new Token(keyword, Token.Type.KEYWORD);
            textBuffer = textBuffer.substring(i);
        } else if (currentChar == '>' || currentChar == '=' || currentChar == '<') {
            currentToken = new Token(Character.toString(currentChar), Token.Type.COMPARISON);
            textBuffer = textBuffer.substring(1);
        } else if (Character.isDigit(currentChar)) {
            int i = 1;
            while (i < textBuffer.length() && Character.isDigit(textBuffer.charAt(i))) {
                i++;
            }
            String number = textBuffer.substring(0, i);
            currentToken = new Token(number, Token.Type.INT);
            textBuffer = textBuffer.substring(i);
        } else {
            textBuffer = textBuffer.substring(1);
            next();
        }
    }

    public List<Token> getAllTokens() {
        List<Token> tokens = new ArrayList<>();
        while (hasNext()) {
            Token token = current();
            if (token.getType() == Token.Type.KEYWORD && !validKeywords.contains(token.getToken())) {
                return new ArrayList<>();
            }
            tokens.add(token);
            next();
        }


        for (int i = 0; i < tokens.size(); i += 3) {
            if (i + 2 >= tokens.size() ||
                    tokens.get(i).getType() != Token.Type.KEYWORD ||
                    tokens.get(i + 1).getType() != Token.Type.COMPARISON ||
                    tokens.get(i + 2).getType() != Token.Type.INT) {
                return new ArrayList<>();
            }
        }

        return tokens;
    }
}
