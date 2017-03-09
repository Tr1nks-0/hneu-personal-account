package edu.hneu.studentsportal.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.hneu.studentsportal.model.type.DisciplineType;

@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    protected String id;
    @NotEmpty
    protected String label;
    @NotNull
    protected DisciplineType type;
    @NotNull
    protected Integer rowInExcelFile;
    protected String credits;
    protected String controlForm;
    protected String mark;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(final String credits) {
        this.credits = credits;
    }

    public String getControlForm() {
        return controlForm;
    }

    public void setControlForm(final String controlForm) {
        this.controlForm = controlForm;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(final String mark) {
        this.mark = mark;
    }

    public DisciplineType getType() {
        return type;
    }

    public void setType(final DisciplineType type) {
        this.type = type;
    }

    public Integer getRowInExcelFile() {
        return rowInExcelFile;
    }

    public void setRowInExcelFile(final Integer rowInExcelFile) {
        this.rowInExcelFile = rowInExcelFile;
    }
}
