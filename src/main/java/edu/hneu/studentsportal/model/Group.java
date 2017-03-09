package edu.hneu.studentsportal.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "group")
@AllArgsConstructor
public class Group {

    @Id
    private String id;
    @NotBlank
    private String name;

}
