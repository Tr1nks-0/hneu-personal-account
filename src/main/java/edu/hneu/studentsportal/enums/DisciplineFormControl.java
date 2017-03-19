package edu.hneu.studentsportal.enums;

public enum DisciplineFormControl {
    REGULAR("Диф. залік"), EXAM("Екзамен"), REPORT("ЗВІТ"),
    GRADUATE_PROJECT("Дипломний проект"), STATE_EXAM("Держ. Екзамен"), COURSE_PROJECT("Курсовий проект");

    private final String name;

    DisciplineFormControl(String name) {
        this.name = name;
    }

    public static DisciplineFormControl of(String name) {
        for(DisciplineFormControl disciplineFormControl : values())
            if(disciplineFormControl.name.equalsIgnoreCase(name)) return disciplineFormControl;
        throw new IllegalArgumentException();
    }
}
