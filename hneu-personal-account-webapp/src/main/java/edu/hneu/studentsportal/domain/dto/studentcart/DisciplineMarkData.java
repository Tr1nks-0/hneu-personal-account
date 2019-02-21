package edu.hneu.studentsportal.domain.dto.studentcart;

public class DisciplineMarkData {


    private DisciplineData discipline;
    private String mark;

    public DisciplineMarkData(DisciplineData discipline, String mark) {
        this.discipline = discipline;
        this.mark = mark;
    }

    public DisciplineData getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineData discipline) {
        this.discipline = discipline;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
