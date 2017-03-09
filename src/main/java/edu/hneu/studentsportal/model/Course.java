package edu.hneu.studentsportal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

    @Id
    private String id;
    private String label;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course2semester",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SEMESTER_ID")
    )
    private List<Semester> semesters;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(final List<Semester> semesters) {
        this.semesters = semesters;
    }
}
