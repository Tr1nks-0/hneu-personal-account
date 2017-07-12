package edu.hneu.studentsportal.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public enum DisciplineDegree {
    MASTER("маг");

    private final String name;

    DisciplineDegree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DisciplineDegree parse(String name) {
        return Arrays.stream(values())
                .filter(degree -> StringUtils.equals(degree.getName(), name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }
}
