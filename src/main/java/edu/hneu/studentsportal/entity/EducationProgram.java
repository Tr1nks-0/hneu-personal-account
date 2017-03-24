package edu.hneu.studentsportal.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "education_program")
public class EducationProgram {

    @Id
    private long id;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Speciality speciality;

}
