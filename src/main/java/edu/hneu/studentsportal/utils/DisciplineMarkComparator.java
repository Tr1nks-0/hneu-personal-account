package edu.hneu.studentsportal.utils;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.DisciplineMark;
import org.apache.commons.lang.StringUtils;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Objects.isNull;

public class DisciplineMarkComparator implements Comparator<DisciplineMark> {

    @Override
    public int compare(DisciplineMark mark1, DisciplineMark mark2) {
        Discipline discipline1 = mark1.getDiscipline();
        Discipline discipline2 = mark2.getDiscipline();
        if(Objects.equals(discipline1.getCourse(), discipline2.getCourse())) {
            if(Objects.equals(discipline1.getSemester(), discipline2.getSemester())) {
                if(isNull(mark1.getId()) || isNull(mark2.getId())) {
                    return discipline1.getLabel().compareTo(discipline2.getLabel());
                }
                return Long.compare(mark1.getId(), mark2.getId());
            }
            return Integer.compare(discipline1.getSemester(),  discipline2.getSemester());
        }
        return Integer.compare(discipline1.getCourse(), discipline2.getCourse());
    }

}
