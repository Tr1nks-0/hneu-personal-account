package edu.hneu.studentsportal.entity;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private Integer mark;
    private Integer credits;

    @NonNull
    private int course;
    @NonNull
    private int semester;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "discipline2education_program",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "education_program_id")
    )
    private EducationProgram educationProgram;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "discipline2speciality",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
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
