package edu.hneu.studentsportal.domain.dto.schedule;


import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"displayName", "title"})
public class Group {

    @XmlElement(name = "display-name", required = true)
    String displayName;

    @XmlElement(required = true)
    String title;

    @XmlAttribute
    String all;

    @XmlAttribute
    Short id;

    @XmlAttribute
    String main;

}
