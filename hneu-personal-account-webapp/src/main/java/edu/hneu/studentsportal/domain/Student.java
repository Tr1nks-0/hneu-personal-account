package edu.hneu.studentsportal.domain;

import edu.hneu.studentsportal.annotation.LimitSemesterDisciplines;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "student")
@ToString(of = "email")
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private long id;

    private Long scheduleStudentId;

    @NotNull
    @Column(unique = true)
    @Length(min = 3, max = 50)
    @Email
    private String email;

    @NotNull
    @Length(min = 3, max = 40)
    private String name;

    @NotNull
    @Length(min = 3, max = 40)
    private String surname;

    @NotNull
    @Column(name = "passport_number")
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private String passportNumber;

    @NotNull
    @Column(name = "income_year")
    private Integer incomeYear;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Faculty faculty;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Speciality speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private EducationProgram educationProgram;

    @Lob
    @NotNull
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_group")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Group group;

    @ElementCollection
    @CollectionTable(name = "student_contact", joinColumns = @JoinColumn(name = "student_email"))
    @Column(name = "contact")
    @Cascade(CascadeType.ALL)
    private List<String> contactInfo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "discipline_mark_id"))
    @Cascade(CascadeType.ALL)
    @LimitSemesterDisciplines(count = 2, type = DisciplineType.MAGMAYNOR)
    private List<DisciplineMark> disciplineMarks;

    private Double averageMark;

    @NotNull
    private Boolean contract;

}
