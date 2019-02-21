package edu.hneu.studentsportal.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class MarkConverterService {
    private static final String NUMBER_REGEX = "\\d{1,2}|100";
    private static final Map<Interval, String> ECTS_MARKS = new HashMap<>();
    private static final Map<Interval, String> NATIONAL_MARKS = new HashMap<>();

    static {
        ECTS_MARKS.put(new Interval(90, 100), "A");
        ECTS_MARKS.put(new Interval(82, 89), "B");
        ECTS_MARKS.put(new Interval(74, 81), "C");
        ECTS_MARKS.put(new Interval(64, 73), "D");
        ECTS_MARKS.put(new Interval(60, 63), "E");
        ECTS_MARKS.put(new Interval(40, 59), "FX");
        ECTS_MARKS.put(new Interval(0, 39), "F");

        NATIONAL_MARKS.put(new Interval(90, 100), "Відмінно");
        NATIONAL_MARKS.put(new Interval(74, 89), "Добре");
        NATIONAL_MARKS.put(new Interval(60, 73), "Задовільно");
        NATIONAL_MARKS.put(new Interval(0, 59), "Незадовільно");
    }

    public String getECTSMark(String mark) {
        return getMark(mark, ECTS_MARKS);
    }

    public String getNationalMark(String mark) {
        return getMark(mark, NATIONAL_MARKS);
    }

    private String getMark(String mark, Map<Interval, String> marks) {
        if (Objects.nonNull(mark) && mark.matches(NUMBER_REGEX)) {
            int value = Integer.parseInt(mark);
            return marks.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().isInRange(value))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("Wrong interval key value"))
                    .getValue();
        } else {
            return mark;
        }
    }

    static class Interval {
        private final int min;
        private final int max;

        Interval(int min, int max) {
            this.min = min;
            this.max = max;
        }

        boolean isInRange(int value) {
            return value <= max && value >= min;
        }
    }

}
