package edu.hneu.studentsportal.entity;

import lombok.Data;
import lombok.ToString;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Faculty faculty;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Speciality speciality;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    private EducationProgram educationProgram;

    @Lob
    @NotNull
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_group")
    private Group studentGroup;

    @ElementCollection
    @CollectionTable(name = "student2contact", joinColumns = @JoinColumn(name = "student_email"))
    @Column(name = "contact")
    private List<String> contactInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "discipline_mark_id"))
    private List<DisciplineMark> disciplineMarks;

}
