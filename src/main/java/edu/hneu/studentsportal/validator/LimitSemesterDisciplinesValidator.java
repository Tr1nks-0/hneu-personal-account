package edu.hneu.studentsportal.validator;

import edu.hneu.studentsportal.annotation.LimitSemesterDisciplines;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.enums.DisciplineType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LimitSemesterDisciplinesValidator implements ConstraintValidator<LimitSemesterDisciplines, List<DisciplineMark>> {

    private int permittedCount;
    private DisciplineType type;

    @Override
    public void initialize(LimitSemesterDisciplines annotation) {
        permittedCount = annotation.count();
        type = annotation.type();
    }

    @Override
    public boolean isValid(List<DisciplineMark> marks, ConstraintValidatorContext context) {
        Map<Integer, Long> collect = marks.stream()
                .map(DisciplineMark::getDiscipline)
                .filter(d -> d.getType().equals(type))
                .collect(Collectors.groupingBy(t -> t.getCourse() * 2 + t.getSemester(), Collectors.counting()));
        return collect.values().stream().noneMatch(count -> count > permittedCount);
    }
}
