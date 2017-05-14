package edu.hneu.studentsportal.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(exclude = "student")
@Entity
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

    private String mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "student_discipline_mark",
            joinColumns = @JoinColumn(name = "discipline_mark_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Student student;

    public DisciplineMark(Discipline discipline, String mark) {
        this.discipline = discipline;
        this.mark = mark;
    }
}
