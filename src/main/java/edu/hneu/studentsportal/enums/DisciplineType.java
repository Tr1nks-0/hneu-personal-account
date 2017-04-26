package edu.hneu.studentsportal.enums;

public enum  DisciplineType {
    MAJOR("Мейджор"), MAYNOR("Майнор"), MAGMAYNOR("Маг-Майнор"), REGULAR("Загальний");

    private final String name;

    DisciplineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
