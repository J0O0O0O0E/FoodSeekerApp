package com.example.myapplication;

import com.example.myapplication.tokenizer.Token;
import com.example.myapplication.tokenizer.Tokenizer;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TestTokenizer {
    String input = "distance>100 rating=5 capacity<10";
    Tokenizer tokenizer = new Tokenizer(input);
    List<Token> tokens = tokenizer.getAllTokens();
    Token token1 = new Token("distance", Token.Type.KEYWORD);
    Token token2 = new Token(">", Token.Type.COMPARISON);
    Token token3 = new Token("100", Token.Type.INT);
    Token token4 = new Token("rating", Token.Type.KEYWORD);
    Token token5 = new Token("=", Token.Type.COMPARISON);
    Token token6 = new Token("5", Token.Type.INT);
    Token token7 = new Token("capacity", Token.Type.KEYWORD);
    Token token8 =  new Token("<", Token.Type.COMPARISON);
    Token token9 = new Token("10", Token.Type.INT);

    @Test
    public void testValidInput() {
        String input = "distance>100 rating=5 capacity<10";
        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.getAllTokens();

        assertEquals(9, tokens.size());
        assertEquals(token1.getToken(), tokens.get(0).getToken());
        assertEquals(token1.getToken(), tokens.get(0).getToken());//1
        assertEquals(token2.getToken(), tokens.get(1).getToken());
        assertEquals(token2.getToken(), tokens.get(1).getToken());//2
        assertEquals(token3.getToken(), tokens.get(2).getToken());
        assertEquals(token3.getToken(), tokens.get(2).getToken());//3
        assertEquals(token4.getToken(), tokens.get(3).getToken());
        assertEquals(token4.getToken(), tokens.get(3).getToken());//4
        assertEquals(token5.getToken(), tokens.get(4).getToken());
        assertEquals(token5.getToken(), tokens.get(4).getToken());//5
        assertEquals(token6.getToken(), tokens.get(5).getToken());
        assertEquals(token6.getToken(), tokens.get(5).getToken());//6
        assertEquals(token7.getToken(), tokens.get(6).getToken());
        assertEquals(token7.getToken(), tokens.get(6).getToken());//7
        assertEquals(token8.getToken(), tokens.get(7).getToken());
        assertEquals(token8.getToken(), tokens.get(7).getToken());//8
        assertEquals(token9.getToken(), tokens.get(8).getToken());
        assertEquals(token9.getToken(), tokens.get(8).getToken());//9
    }

    @Test
    public void testInvalidKeyword() {
        String input = "invalid > 100";
        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.getAllTokens();

        assertTrue(tokens.isEmpty());
    }

    @Test
    public void testInvalidFormat() {
        String input = "distance > 100 rating 5";
        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.getAllTokens();

        assertTrue(tokens.isEmpty());
    }

    @Test
    public void testEmptyInput() {
        String input = "";
        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.getAllTokens();

        assertTrue(tokens.isEmpty());
    }
}