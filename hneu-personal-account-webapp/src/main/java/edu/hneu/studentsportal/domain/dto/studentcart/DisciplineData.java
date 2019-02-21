package edu.hneu.studentsportal.domain.dto.studentcart;

import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;

public class DisciplineData {

    private String code;
    private Integer credits;
    private DisciplineFormControl controlForm;
    private String label;
    private boolean secondary;
    private DisciplineType type;

    public DisciplineData(String code, Integer credits, DisciplineFormControl controlForm, String label, boolean secondary, DisciplineType type) {
        this.code = code;
        this.credits = credits;
        this.controlForm = controlForm;
        this.label = label;
        this.secondary = secondary;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public DisciplineFormControl getControlForm() {
        return controlForm;
    }

    public void setControlForm(DisciplineFormControl controlForm) {
        this.controlForm = controlForm;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }

    public DisciplineType getType() {
        return type;
    }

    public void setType(DisciplineType type) {
        this.type = type;
    }
}
