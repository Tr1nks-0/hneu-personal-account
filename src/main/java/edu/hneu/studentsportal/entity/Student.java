package edu.hneu.studentsportal.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "student")
@ToString(of = "email")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String surname;
    private String passportNumber;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2faculty",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id")
    )
    private Faculty faculty;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2speciality",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Speciality speciality;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2education_program",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "education_program_id")
    )
    private EducationProgram educationProgram;

    private Integer incomeYear;
    private Integer rate;
    private Double average;
    private Long modificationTime;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Group studentGroup;

    @ElementCollection
    @CollectionTable(name = "student2contact", joinColumns = @JoinColumn(name = "student_email"))
    @Column(name = "contact")
    private List<String> contactInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2discipline",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines;

}
