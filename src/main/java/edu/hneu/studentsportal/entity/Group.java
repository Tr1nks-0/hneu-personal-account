package edu.hneu.studentsportal.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "student_group")
@AllArgsConstructor
@RequiredArgsConstructor
public class Group {

    @Id
    @Generated
    private String id;

    @NonNull
    @NotBlank
    private String name;

}
