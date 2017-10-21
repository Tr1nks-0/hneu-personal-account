package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"displayName"})
public class Teacher {

    @XmlElement(name = "display-name", required = true)
    String displayName;

    @XmlAttribute(name = "full-name")
    String fullName;

    @XmlAttribute
    Integer id;

}
