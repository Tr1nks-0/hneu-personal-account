package edu.hneu.studentsportal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

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
    private Integer incomeYear;
    private String speciality;
    private String group;
    private String groupId;
    private String password;
    private String profileImage;
    private String passportNumber;
    private String filePath;
    private long modificationTime;
    private Double average;
    private Integer specialityPlace;

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
