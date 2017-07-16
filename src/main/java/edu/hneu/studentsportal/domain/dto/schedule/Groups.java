package edu.hneu.studentsportal.domain.dto.schedule;

import lombok.Data;
import lombok.Value;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"group"})
public class Groups {

    List<Group> group = new ArrayList<>();

}
