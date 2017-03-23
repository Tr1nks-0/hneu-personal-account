package edu.hneu.studentsportal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "discipline_mark")
@NoArgsConstructor
@RequiredArgsConstructor
public class DisciplineMark {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Discipline discipline;

    private Double mark;

    public DisciplineMark(Discipline discipline, double mark) {
        this.discipline = discipline;
        this.mark = mark;
    }

}
