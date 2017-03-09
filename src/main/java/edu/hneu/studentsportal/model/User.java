package edu.hneu.studentsportal.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import edu.hneu.studentsportal.model.type.UserRole;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }
}
