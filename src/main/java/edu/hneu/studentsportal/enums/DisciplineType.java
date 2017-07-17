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

    public static DisciplineType of(String name) {
        for(DisciplineType type : values())
            if(type.name.equalsIgnoreCase(name))
                return type;
        throw new IllegalArgumentException("Cannot find discipline type for name: " + name);
    }
}
