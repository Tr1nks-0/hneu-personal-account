package edu.hneu.studentsportal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name = "education_program", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "speciality_id"})})
@NoArgsConstructor
@RequiredArgsConstructor
public class EducationProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Length(min = 3, max = 200)
    private String name;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Speciality speciality;

}
