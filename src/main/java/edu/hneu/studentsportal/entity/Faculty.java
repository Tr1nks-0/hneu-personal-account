package edu.hneu.studentsportal.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    private long id;

    @NotNull
    private String name;

}
