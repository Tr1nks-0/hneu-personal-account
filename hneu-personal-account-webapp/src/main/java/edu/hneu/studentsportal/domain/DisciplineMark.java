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

    private String mark;
    private String previousMark;

    public DisciplineMark(Discipline discipline, String mark) {
        this.discipline = discipline;
        this.mark = mark;
    }
}
