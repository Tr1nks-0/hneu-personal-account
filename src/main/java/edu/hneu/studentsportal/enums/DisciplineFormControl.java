package edu.hneu.studentsportal.enums;

public enum DisciplineFormControl {

    REGULAR("Залік"), EXAM("Екзамен");

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
