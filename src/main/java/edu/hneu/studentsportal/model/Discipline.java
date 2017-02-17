package edu.hneu.studentsportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.hneu.studentsportal.model.type.DisciplineType;

@Document(collection = "Discipline")
public class Discipline {

    @Id
    protected String id;
    protected String label;
    protected String credits;
    protected String controlForm;
    protected String mark;
    protected DisciplineType type;
    protected Integer rowInExcelFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
