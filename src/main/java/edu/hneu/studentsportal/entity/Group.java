package edu.hneu.studentsportal.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "student_group")
public class Group {

    @Id
    @Min(value = 0, message = "invalid.group.code")
    @Max(value = Integer.MAX_VALUE, message = "invalid.group.code")
    private long id;

    @NotNull
    @Size(min = 2, max = 100, message = "invalid.group.name")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Speciality speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_program_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private EducationProgram educationProgram;
}
