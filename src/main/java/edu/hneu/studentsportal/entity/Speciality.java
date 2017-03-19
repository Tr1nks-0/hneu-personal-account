package edu.hneu.studentsportal.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@Entity
@Table(name = "speciality")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Speciality {

    @Id
    private long id;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "speciality2faculty",
            joinColumns = @JoinColumn(name = "speciality_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id")
    )
    private Faculty faculty;

}
