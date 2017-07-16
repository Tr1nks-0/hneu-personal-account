
package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;
import lombok.Value;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "metadata",
        "week",
        "scheduleElements"
})
@XmlRootElement(name = "schedule")
public class Schedule {

    @XmlElement(required = true)
    Metadata metadata;

    @XmlElement(required = true)
    Week week;

    @XmlElement(name = "schedule-elements", required = true)
    ScheduleElements scheduleElements;

    @XmlAttribute(name = "time-zone")
    String timeZone;

}
