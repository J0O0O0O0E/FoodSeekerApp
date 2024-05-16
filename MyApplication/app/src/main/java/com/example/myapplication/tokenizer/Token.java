package com.example.myapplication.tokenizer;

/**
 * The Token class represents a token with a string value and a type.
 * Tokens are used in parsing and tokenization processes, to
 * break down input data into meaningful components for further processing.
 * @author Zijian Yang u7724610
 */
public class Token {
    // The string value of the token
    private String token;
    // The type of the token
    private Type type;

    /**
     * The Type enum defines the possible types of tokens.
     * KEYWORD represents tokens that are keywords.
     * COMPARISON represents tokens that are comparison operators.
     * INT represents tokens that are integer values.
     */
    public enum Type {
        KEYWORD, COMPARISON, INT
    }

    /**
     * Constructs a new Token with the specified string value and type.
     *
     * @param token The string value of the token.
     * @param type  The type of the token.
     */
    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    /**
     * Gets the string value of the token.
     *
     * @return The string value of the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the type of the token.
     *
     * @return The type of the token.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns a string representation of the token, including its value and type.
     *
     * @return A string representation of the token.
     */
    @Override
    public String toString() {
        return String.format("Token[%s, %s]", token, type);
    }
}
