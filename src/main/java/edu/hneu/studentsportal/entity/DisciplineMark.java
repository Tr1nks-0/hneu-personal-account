package edu.hneu.studentsportal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Discipline discipline;

    private Double mark;

    public DisciplineMark(Discipline discipline, double mark) {
        this.discipline = discipline;
        this.mark = mark;
    }

}
