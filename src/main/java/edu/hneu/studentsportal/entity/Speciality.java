package edu.hneu.studentsportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "speciality")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Speciality {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne
    private Faculty faculty;

}
