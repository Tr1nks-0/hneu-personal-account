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
    @GeneratedValue
    private long id;

    @NotBlank
    private String label;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course2semester",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id")
    )
    private List<Semester> semesters;

}
