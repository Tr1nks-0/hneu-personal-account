package edu.hneu.studentsportal.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "faculty")
@NoArgsConstructor
@RequiredArgsConstructor
public class Faculty {

    @Id
    private long id;

    @NonNull
    @NotBlank
    private String name;

}
