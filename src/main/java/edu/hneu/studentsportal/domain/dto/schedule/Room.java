package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"displayName"})
public class Room {

    @XmlElement(name = "display-name", required = true)
    String displayName;

    @XmlAttribute
    String building;

    @XmlAttribute
    Short id;

    @XmlAttribute
    String name;

    @XmlAttribute(name = "short-name")
    String shortName;

}
