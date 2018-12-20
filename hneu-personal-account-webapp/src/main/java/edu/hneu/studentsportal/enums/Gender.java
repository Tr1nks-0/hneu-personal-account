package edu.hneu.studentsportal.enums;

import java.util.Arrays;

public enum Gender {
    UNDEFINED(""),
    MALE("(?iu)male|мужской|чоловіча|m|м|ч"),
    FEMALE("(?iu)female|женский|жіноча|f|ж");


    private String regex;

    Gender(String regex) {
        this.regex = regex;
    }

    public static Gender getByString(String str) {
        return Arrays.stream(Gender.values())
                .filter(g -> !UNDEFINED.equals(g))
                .filter(gender -> str.matches(gender.regex)).findAny().orElse(UNDEFINED);
    }
}
