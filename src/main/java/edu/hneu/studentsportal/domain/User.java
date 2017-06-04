package edu.hneu.studentsportal.domain;

import edu.hneu.studentsportal.enums.UserRole;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

}
