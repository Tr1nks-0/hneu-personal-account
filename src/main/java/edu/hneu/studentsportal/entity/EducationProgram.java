package edu.hneu.studentsportal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "education_program")
public class EducationProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Length(min = 3)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Speciality speciality;

}
