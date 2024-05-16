package com.example.myapplication.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Tokenizer class is responsible for breaking down a string of text into a series of tokens.
 * These tokens can then be used for further processing, such as parsing or evaluating expressions.
 * @author Zijian Yang u7724610, Si Chen u7756543
 */
public class Tokenizer {
    // Buffer to hold the text being tokenized
    private String textBuffer;
    // The current token being processed
    private Token currentToken;

    // List of valid keywords that the tokenizer recognizes
    private static final ArrayList<String> validKeywords = new ArrayList<>(Arrays.asList("rating", "capacity"));

    /**
     * Constructs a new Tokenizer with the specified text.
     *
     * @param text The text to be tokenized.
     */
    public Tokenizer(String text) {
        textBuffer = text;
        next();
    }

    /**
     * Returns the current token.
     *
     * @return The current token.
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Checks if there are more tokens to process.
     *
     * @return True if there are more tokens, false otherwise.
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    /**
     * Advances to the next token in the text buffer.
     * This method processes the text buffer to extract the next token based on the current character.
     */
    public void next() {
        // Trim leading whitespace from the text buffer
        textBuffer = textBuffer.trim();
        if (textBuffer.isEmpty()) {
            currentToken = null;
            return;
        }

        char currentChar = textBuffer.charAt(0);

        // Check if the current character is a letter (indicating a keyword)
        if (Character.isLetter(currentChar)) {
            int i = 1;
            while (i < textBuffer.length() && Character.isLetter(textBuffer.charAt(i))) {
                i++;
            }
            String keyword = textBuffer.substring(0, i);
            currentToken = new Token(keyword, Token.Type.KEYWORD);
            textBuffer = textBuffer.substring(i);
        }
        // Check if the current character is a comparison operator
        else if (currentChar == '>' || currentChar == '=' || currentChar == '<') {
            currentToken = new Token(Character.toString(currentChar), Token.Type.COMPARISON);
            textBuffer = textBuffer.substring(1);
        }
        // Check if the current character is a digit (indicating an integer)
        else if (Character.isDigit(currentChar)) {
            int i = 1;
            while (i < textBuffer.length() && Character.isDigit(textBuffer.charAt(i))) {
                i++;
            }
            String number = textBuffer.substring(0, i);
            currentToken = new Token(number, Token.Type.INT);
            textBuffer = textBuffer.substring(i);
        }
        // If the current character is not recognized, skip it and process the next character
        else {
            textBuffer = textBuffer.substring(1);
            next();
        }
    }

    /**
     * Returns a list of all tokens in the text buffer.
     * This method processes the entire text buffer to extract all valid tokens.
     *
     * @return A list of all tokens in the text buffer, or an empty list if invalid tokens are encountered.
     */
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

        // Validate the sequence of tokens (keyword, comparison, integer)
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
