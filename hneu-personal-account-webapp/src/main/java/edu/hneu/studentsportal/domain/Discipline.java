package edu.hneu.studentsportal.domain;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@Table(name = "discipline", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "code", "label", "course", "semester", "education_program_id", "speciality_id"
        })
})
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"label"})
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(max = 100)
    private String code;

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

    @ManyToOne
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @NotNull
    @ManyToOne
    private Speciality speciality;

    @NotNull
    @Column(name = "control_form")
    @Enumerated(EnumType.ORDINAL)
    private DisciplineFormControl controlForm;

    @NotNull
    @Length(min = 3, max = 100)
    private String label;

    private boolean secondary;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;

    public Discipline(String label, DisciplineType type, int course, int semester) {
        this.label = label;
        this.type = type;
        this.course = course;
        this.semester = semester;
    }
}
