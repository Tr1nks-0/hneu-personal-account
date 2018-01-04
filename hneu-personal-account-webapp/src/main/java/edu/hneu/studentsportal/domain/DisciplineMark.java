package edu.hneu.studentsportal.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "discipline_mark")
@NoArgsConstructor
@RequiredArgsConstructor
public class DisciplineMark {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Discipline discipline;

    @NonNull
    @ManyToOne
    @Cascade(CascadeType.ALL)
    private Student student;

    private String mark;

    public DisciplineMark(Discipline discipline) {
        this.discipline = discipline;
    }
}
