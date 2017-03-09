package edu.hneu.studentsportal.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.hneu.studentsportal.model.type.DisciplineType;
import lombok.Data;

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
