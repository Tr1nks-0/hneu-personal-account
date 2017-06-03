package edu.hneu.studentsportal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "student_group")
public class Group {

    @Id
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private long id;

    @NotNull
    @Length(min = 2, max = 100)
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Speciality speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

}
