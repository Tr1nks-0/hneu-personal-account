package edu.hneu.studentsportal.parser.util;

public class Indexer {

    private int value = 0;

    private Indexer(int start) {
        value = start;
    }

    public static Indexer of(int start) {
        return new Indexer(start);
    }

    public int next() {
        return ++value;
    }
}
