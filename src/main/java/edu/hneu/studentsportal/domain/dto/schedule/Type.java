package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
public class Type {

    @XmlValue
    String value;

    @XmlAttribute
    Byte id;

    @XmlAttribute
    String type;

}
