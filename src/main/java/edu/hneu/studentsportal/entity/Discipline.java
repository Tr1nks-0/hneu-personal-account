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
    protected String id;

    protected String credits;
    protected String controlForm;
    protected String mark;

    @NotEmpty
    protected String label;
    @NotNull
    protected Integer rowInExcelFile;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    protected DisciplineType type;

}
