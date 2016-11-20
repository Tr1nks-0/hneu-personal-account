package edu.hneu.studentsportal.pojo;

import java.util.List;

public class StudentDisciplines {

    private List<StudentDiscipline> list;

    public StudentDisciplines() {}

    public StudentDisciplines(List<StudentDiscipline> list) {
        this.list = list;
    }

    public List<StudentDiscipline> getList() {
        return list;
    }

    public void setList(List<StudentDiscipline> list) {
        this.list = list;
    }
}
