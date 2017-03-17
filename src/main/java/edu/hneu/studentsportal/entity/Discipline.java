package edu.hneu.studentsportal.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.Data;

@Data
@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    @GeneratedValue
    private long id;

    private Integer mark;
    private Integer credits;
    private String controlForm;

    @NotEmpty
    private String label;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;

}
