package com.example.myapplication.tokenizer;

import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class Tokenizer {
    public abstract boolean hasNext();
    public abstract boolean next();
    public abstract Token current();
}
