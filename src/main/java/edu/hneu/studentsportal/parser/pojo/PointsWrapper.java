package edu.hneu.studentsportal.parser.pojo;

import java.util.List;
import java.util.Map;

import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Student;
import lombok.Data;

@Data
public class PointsWrapper {

    private Map<Student, List<DisciplineMark>> map;

}
