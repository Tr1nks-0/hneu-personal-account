package edu.hneu.studentsportal.enums;

public enum DisciplineFormControl {
    REGULAR("Диф. залік"),
    EXAM("Екзамен"),
    STATE_EXAM("Держ. Екзамен"),
    REPORT("ЗВІТ"),
    GRADUATE_TASK("Дипломна робота"),
    GRADUATE_PROJECT("Дипломний проект"),
    COURSE_PROJECT("Курсовий проект");

    private final String name;

    DisciplineFormControl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DisciplineFormControl of(String name) {
        for(DisciplineFormControl disciplineFormControl : values())
            if(disciplineFormControl.name.equalsIgnoreCase(name)) return disciplineFormControl;
        throw new IllegalArgumentException("Cannot find discipline form control for name: " + name);
    }
}
