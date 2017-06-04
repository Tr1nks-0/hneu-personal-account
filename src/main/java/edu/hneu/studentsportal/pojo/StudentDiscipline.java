package edu.hneu.studentsportal.pojo;

import edu.hneu.studentsportal.domain.Discipline;

public class StudentDiscipline extends Discipline {

    private String name;
    private String surname;
    private String studentId;
    private Integer number;
    private Discipline discipline;

    public StudentDiscipline() {
    }

    public StudentDiscipline(final String studentId, final String name, final String surname, final Integer number, final Discipline discipline) {
        this.name = name;
        this.studentId = studentId;
        this.surname = surname;
        this.number = number;
        this.discipline = discipline;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(final String studentId) {
        this.studentId = studentId;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }
}
