package edu.hneu.studentsportal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Faculty faculty;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Speciality speciality;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DisciplineMark> disciplineMarks;

}
