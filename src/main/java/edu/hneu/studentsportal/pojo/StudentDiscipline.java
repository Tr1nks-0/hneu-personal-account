package edu.hneu.studentsportal.pojo;

import edu.hneu.studentsportal.model.Discipline;

public class StudentDiscipline extends Discipline {

    private String name;
    private String surname;
    private String studentId;
    private Integer number;

    public StudentDiscipline() {}

    public StudentDiscipline(String studentId, String name, String surname, Integer number,  Discipline discipline) {
        this.name = name;
        this.studentId = studentId;
        this.surname = surname;
        this.number = number;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
