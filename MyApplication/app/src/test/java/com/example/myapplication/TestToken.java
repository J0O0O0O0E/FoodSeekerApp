package com.example.myapplication;
import com.example.myapplication.tokenizer.Token;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestToken {

    private Token token1, token2, token3, token4, token5, token6, token7;

    @Before
    public void setUp(){
        token1 = new Token("capacity", Token.Type.KEYWORD);
        token2 = new Token("rating", Token.Type.KEYWORD);
        token3 = new Token(">", Token.Type.COMPARISON);
        token4 = new Token("=", Token.Type.COMPARISON);
        token5 = new Token("<", Token.Type.COMPARISON);
        token6 = new Token("20", Token.Type.INT);
        token7 = new Token("5", Token.Type.INT);
    }

    @Test
    public void testKeyword() {
        assertEquals("capacity", token1.getToken());
        assertEquals(Token.Type.KEYWORD, token1.getType());
        assertEquals("rating", token2.getToken());
        assertEquals(Token.Type.KEYWORD, token2.getType());
    }

    @Test
    public void testComparison(){
        assertEquals(">", token3.getToken());
        assertEquals(Token.Type.COMPARISON, token3.getType());
        assertEquals("=", token4.getToken());
        assertEquals(Token.Type.COMPARISON, token4.getType());
        assertEquals("<", token5.getToken());
        assertEquals(Token.Type.COMPARISON, token5.getType());
    }

    @Test
    public void testInt(){
        assertEquals("20", token6.getToken());
        assertEquals(Token.Type.INT, token6.getType());
        assertEquals("5", token7.getToken());
        assertEquals(Token.Type.INT, token7.getType());
    }

    @Test
    public void testToString(){
        assertEquals("Token[capacity, KEYWORD]", token1.toString());
        assertEquals("Token[rating, KEYWORD]", token2.toString());
        assertEquals("Token[>, COMPARISON]", token3.toString());
        assertEquals("Token[=, COMPARISON]", token4.toString());
        assertEquals("Token[<, COMPARISON]", token5.toString());
        assertEquals("Token[20, INT]", token6.toString());
        assertEquals("Token[5, INT]", token7.toString());
    }



}
