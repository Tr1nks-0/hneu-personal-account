package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
public class Day {

    @XmlValue
    String value;

    @XmlAttribute
    String date;

    @XmlAttribute
    Byte day;

    @XmlAttribute(name = "display-name")
    String displayName;

    @XmlAttribute
    String name;
}