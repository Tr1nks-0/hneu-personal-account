package edu.hneu.studentsportal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Discipline discipline;

    private Integer mark;

}
