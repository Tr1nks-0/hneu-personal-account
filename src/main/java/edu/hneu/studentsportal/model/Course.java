package edu.hneu.studentsportal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    private String id;
    @NotBlank
    private String label;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course2semester",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SEMESTER_ID")
    )
    private List<Semester> semesters;

}
