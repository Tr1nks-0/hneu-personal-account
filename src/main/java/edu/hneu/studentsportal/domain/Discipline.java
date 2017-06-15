package edu.hneu.studentsportal.domain;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "discipline", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "label", "course", "semester", "education_program_id", "speciality_id"
        })
})
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer credits;

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    private Integer course;

    @NotNull
    @Min(value = 1)
    @Max(value = 2)
    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Speciality speciality;

    @NotNull
    @Column(name="control_form")
    @Enumerated(EnumType.ORDINAL)
    private DisciplineFormControl controlForm;

    @NotNull
    @Length(min = 3, max = 100)
    private String label;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;

    public Discipline(String label, DisciplineType type, int course, int semester) {
        this.label = label;
        this.type = type;
        this.course = course;
        this.semester = semester;
    }

    public Discipline() {
    }

    @Override
    public String toString() {
        return type.getName() + " - " + label;
    }
}
