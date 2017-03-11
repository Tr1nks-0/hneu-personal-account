package edu.hneu.studentsportal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "semester")
public class Semester {

    @Id
    private String id;
    @NotBlank
    private String label;
    private Integer total;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "semester2discipline",
            joinColumns = @JoinColumn(name = "SEMESTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID")
    )
    private List<Discipline> disciplines;

}
