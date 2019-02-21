package edu.hneu.studentsportal.domain.dto.studentcart;

import java.util.List;

public class CourseData {
    private int number;
    private List<SemesterData> semesters;

    public CourseData(int number, List<SemesterData> semesters) {
        this.number = number;
        this.semesters = semesters;
    }

    public int disciplineMarksCount() {
        return semesters.stream().mapToInt(s -> s.getDisciplineMarks().size()).sum();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<SemesterData> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SemesterData> semesters) {
        this.semesters = semesters;
    }
}
