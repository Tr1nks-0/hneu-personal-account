package edu.hneu.studentsportal.domain.dto.studentcart;

import java.util.List;

public class SemesterData {
    private static final String[] SEMESTERS_NAMES = {"ПЕРШИЙ", "ДРУГИЙ"};

    private int number;
    private List<DisciplineMarkData> disciplineMarks;

    public SemesterData(int number, List<DisciplineMarkData> disciplineMarks) {
        this.number = number;
        this.disciplineMarks = disciplineMarks;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<DisciplineMarkData> getDisciplineMarks() {
        return disciplineMarks;
    }

    public void setDisciplineMarks(List<DisciplineMarkData> disciplineMarks) {
        this.disciplineMarks = disciplineMarks;
    }
}
