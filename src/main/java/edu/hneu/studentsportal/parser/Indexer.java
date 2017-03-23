package edu.hneu.studentsportal.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Indexer {

    private int value = 0;

    public int next() {
        return ++value;
    }

    public int skip2() {
        next();
        next();
        return value;
    }
}
