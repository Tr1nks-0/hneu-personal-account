package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
public class Subject {

    @XmlValue
    String value;

    @XmlAttribute
    Short id;

    @XmlAttribute(name = "short-name")
    String shortName;

}