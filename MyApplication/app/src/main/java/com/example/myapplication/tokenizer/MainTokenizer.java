package com.example.myapplication.tokenizer;

import java.util.Arrays;

public class MainTokenizer extends Tokenizer {
    private String textBuffer;
    private Token currentToken;

    public MainTokenizer(String text) {
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
        if (currentChar == '&') {
            currentToken = new Token("&", Token.Type.AND);
        }
        if (currentChar == '=' || currentChar == '<' || currentChar == '>') {
            currentToken = new Token(Character.toString(currentChar), Token.Type.COMPARISON);
        }
        if (Character.isDigit(currentChar)) {
            String currentKeyword = Character.toString(currentChar);
            for (int i = 1; i < textBuffer.length() && Character.isDigit(textBuffer.charAt(i)); i++) {
                currentKeyword += textBuffer.charAt(i);
            }
            currentToken = new Token(currentKeyword, Token.Type.INT);
        }
        if (Character.isLetter(currentChar)) {
            int word_length = 0;
            while (word_length < textBuffer.length() && Character.isLetter(textBuffer.charAt(word_length))) {
                word_length++;
            }
            String extractKeyword = textBuffer.substring(0, word_length).toLowerCase();
            if (Token.containsCapacity(extractKeyword)) {
                extractKeyword = "capacity";
            } else if (Token.containsRating(extractKeyword)) {
                extractKeyword = "rating";
            } else if (Token.containsName(extractKeyword)) {
                extractKeyword = "name";
            }
            if (Arrays.asList(Token.keyword).contains(extractKeyword.toLowerCase())) {
                currentToken = new Token(extractKeyword, Token.Type.KEYWORD);
            } else {
                for (word_length = extractKeyword.length(); word_length < textBuffer.length() && !Character.toString(textBuffer.charAt(word_length)).equals("&"); word_length++)
                    extractKeyword += textBuffer.charAt(word_length);
                currentToken = new Token(extractKeyword, Token.Type.NAME);

            }
        }
        textBuffer = textBuffer.substring(currentToken.getToken().length());
    }
}
