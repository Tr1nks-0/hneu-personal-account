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
    @GeneratedValue
    private long id;

    @NotBlank
    private String label;

    private Integer total;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "semester2discipline",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;

}
