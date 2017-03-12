package edu.hneu.studentsportal.entity;

import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    @GeneratedValue
    private long id;

    private String credits;
    private String controlForm;
    private String mark;

    @NotEmpty
    private String label;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DisciplineType type;

}
