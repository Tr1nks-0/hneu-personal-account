package edu.hneu.studentsportal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@Entity
@Table(name = "discipline")
@NoArgsConstructor
@AllArgsConstructor
public class Discipline {

    @Id
    @GeneratedValue
    private long id;

    private Integer credits;

    @NonNull
    private int course;
    @NonNull
    private int semester;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EducationProgram educationProgram;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Speciality speciality;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineFormControl controlForm;

    @NotEmpty
    private String label;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;

}
