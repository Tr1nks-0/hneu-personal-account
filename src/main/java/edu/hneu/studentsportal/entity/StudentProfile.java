package edu.hneu.studentsportal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "student")
public class StudentProfile {

    @Id
    private String id;

    private String email;
    private String role;
    private String name;
    private String surname;
    private String faculty;
    private String speciality;
    private String passportNumber;
    private String filePath;
    private Long modificationTime;
    private Double average;
    private Integer incomeYear;
    private Integer rate;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;

    @ManyToOne(cascade=CascadeType.ALL)
    private Group studentGroup;

    @ElementCollection
    @CollectionTable(name="student2contacts", joinColumns=@JoinColumn(name="STUDENT_ID"))
    @Column(name="contact")
    private List<String> contactInfo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student2course",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID")
    )
    private List<Course> courses;
}
