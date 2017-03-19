package edu.hneu.studentsportal.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@Entity
@Table(name = "education_program")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class EducationProgram {

    @Id
    private long id;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "education_program2faculty",
            joinColumns = @JoinColumn(name = "education_program_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Speciality speciality;

}
