package edu.hneu.studentsportal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "education_program")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class EducationProgram {

    @Id
    private long id;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Speciality speciality;

}
