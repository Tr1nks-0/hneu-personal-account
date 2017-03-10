package edu.hneu.studentsportal.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import edu.hneu.studentsportal.enums.UserRole;
import lombok.Data;

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
