package edu.hneu.studentsportal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "semester")
public class Semester {

    @Id
    private String id;
    @NotBlank
    private String label;
    private Integer total;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "semester2discipline",
            joinColumns = @JoinColumn(name = "SEMESTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID")
    )
    private List<Discipline> disciplines;

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

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(final List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }
}
