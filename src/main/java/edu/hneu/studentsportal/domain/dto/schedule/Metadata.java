package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
public class Metadata {

    @XmlValue
    String value;

    @XmlAttribute
    String description;

    @XmlAttribute(name = "end-date")
    String endDate;

    @XmlAttribute(name = "has-three-last-pairs")
    String hasThreeLastPairs;

    @XmlAttribute(name = "has-weekend-pairs")
    String hasWeekendPairs;

    @XmlAttribute
    Short id;

    @XmlAttribute
    String name;

    @XmlAttribute(name = "short-name")
    String shortName;

    @XmlAttribute(name = "start-date")
    String startDate;

    @XmlAttribute
    String type;

    @XmlAttribute
    Short year;

    @XmlAttribute(name = "year-display-name")
    String yearDisplayName;

}
