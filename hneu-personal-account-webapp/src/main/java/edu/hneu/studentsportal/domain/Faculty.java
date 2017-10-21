package edu.hneu.studentsportal.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max = 20)
    @Column(unique = true)
    private String code;

    @Length(min = 3, max = 200)
    @Column(unique = true)
    private String name;

}
