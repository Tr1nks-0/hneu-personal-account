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
