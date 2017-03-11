package edu.hneu.studentsportal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

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
