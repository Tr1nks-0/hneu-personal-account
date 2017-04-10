package edu.hneu.studentsportal.entity;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Min(value = 0, message = "invalid.discipline.credits")
    @Max(value = Integer.MAX_VALUE, message = "invalid.discipline.credits")
    private Integer credits;

    @NotNull
    @Min(value = 1, message = "invalid.discipline.course")
    @Max(value = 6, message = "invalid.discipline.course")
    private Integer course;

    @NotNull
    @Min(value = 1, message = "invalid.discipline.semester")
    @Max(value = 2, message = "invalid.discipline.semester")
    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Speciality speciality;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineFormControl controlForm;

    @NotNull
    @Size(min = 3, max = 100, message = "invalid.discipline.name")
    private String label;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;
}
