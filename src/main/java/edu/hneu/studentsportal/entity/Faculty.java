package edu.hneu.studentsportal.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

}
