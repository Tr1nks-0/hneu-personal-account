package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "subject",
        "type",
        "teacher",
        "room",
        "groups",
        "scheduleElement"
})
public class ScheduleElement {

    private Subject subject;
    private Type type;
    private Teacher teacher;
    private Room room;
    private Groups groups;

    @XmlElement(name = "schedule-element")
    private List<ScheduleElement> scheduleElement = new ArrayList<>();

    @XmlAttribute(name = "break-end")
    private String breakEnd;

    @XmlAttribute(name = "break-end-epoch")
    private Integer breakEndEpoch;

    @XmlAttribute(name = "break-start")
    private String breakStart;

    @XmlAttribute(name = "break-start-epoch")
    private Integer breakStartEpoch;

    @XmlAttribute
    private String date;

    @XmlAttribute
    private Byte day;

    @XmlAttribute
    private String end;

    @XmlAttribute(name = "end-epoch")
    private Integer endEpoch;

    @XmlAttribute
    private Byte pair;

    @XmlAttribute
    private String start;

    @XmlAttribute(name = "start-epoch")
    private Integer startEpoch;

    @XmlAttribute(name = "contains-multiple")
    private String containsMultiple;

}
