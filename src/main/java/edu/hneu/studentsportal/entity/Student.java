package edu.hneu.studentsportal.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "student")
@ToString(of = "email")
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(name = "passport_number")
    private String passportNumber;

    @NotNull
    @Column(name = "income_year")
    private Integer incomeYear;

    private Integer rate;
    private Double average;

    @Column(name = "modification_time")
    private Long modificationTime;

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
    private Group studentGroup;

    @ElementCollection
    @CollectionTable(name = "student_contact", joinColumns = @JoinColumn(name = "student_email"))
    @Column(name = "contact")
    @Cascade(CascadeType.ALL)
    private List<String> contactInfo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "discipline_mark_id"))
    @Cascade(CascadeType.ALL)
    private List<DisciplineMark> disciplineMarks;

}
