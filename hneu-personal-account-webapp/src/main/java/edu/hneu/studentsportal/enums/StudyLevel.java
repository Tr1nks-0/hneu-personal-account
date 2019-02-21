package edu.hneu.studentsportal.enums;

import java.util.Arrays;

public enum StudyLevel {
    LOW_SPECIALIST(5, "Молодший спецiалiст"),
    BACHELOR(6, "Бакалавр"),
    SPECIALIST(7, "Cпецiалiст"),
    MASTER(8, "Магiстр");

    private final int levelId;
    private final String name;

    StudyLevel(int levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

    public static StudyLevel byLevelId(int id) {
        return Arrays.stream(values())
                .filter(studyLevel -> studyLevel.levelId == id)
                .findAny()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    public int getLevelId() {
        return levelId;
    }
}
