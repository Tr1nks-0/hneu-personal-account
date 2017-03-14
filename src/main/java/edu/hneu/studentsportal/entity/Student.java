package edu.hneu.studentsportal.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "student")
public class Student {

    @Id
    private String email;

    private String name;
    private String surname;
    private String passportNumber;

    private String faculty;
    private String speciality;
    private String educationProgram;

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
            name = "student2course",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}
