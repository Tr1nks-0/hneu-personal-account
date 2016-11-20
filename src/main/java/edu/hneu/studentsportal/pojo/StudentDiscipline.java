package edu.hneu.studentsportal.pojo;

import edu.hneu.studentsportal.model.Discipline;

public class StudentDiscipline extends Discipline {

    private String name;
    private String surname;
    private int number;

    public StudentDiscipline() {}

    public StudentDiscipline(String name, String surname, int number, Discipline discipline) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        id = discipline.getId();
        label = discipline.getLabel();
        credits = discipline.getCredits();
        controlForm = discipline.getControlForm();
        mark = discipline.getMark();
        type = discipline.getType();
        rowInExcelFile = discipline.getRowInExcelFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
