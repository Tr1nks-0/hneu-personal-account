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
@Table(name = "speciality")
@NoArgsConstructor
@RequiredArgsConstructor
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 3, max = 200)
    @Column(unique = true)
    private String name;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;

}
