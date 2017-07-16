package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.conditions.DisciplineConditions;
import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Service
public class DisciplineMarkService {

    @Resource
    private DisciplineRepository disciplineRepository;

    public List<DisciplineMark> getStudentMarks(Student student, int course, int semester) {
        Predicate<DisciplineMark> hasGivenCourse = m -> m.getDiscipline().getCourse() == course;
        Predicate<DisciplineMark> hasGivenSemester = m -> m.getDiscipline().getSemester() == semester;
        return student.getDisciplineMarks().stream()
                .filter(m -> nonNull(m.getDiscipline()))
                .filter(hasGivenCourse.and(hasGivenSemester)).collect(toList());
    }

    public List<Discipline> getPossibleNewDisciplinesForStudent(Student student, int course, int semester) {
        Speciality speciality = student.getSpeciality();
        EducationProgram educationProgram = student.getEducationProgram();
        List<Discipline> exceptDisciplines = extract(getStudentMarks(student, course, semester), DisciplineMark::getDiscipline);
        Predicate<Discipline> wasNotPickedForStudent = discipline -> isFalse(exceptDisciplines.contains(discipline));
        return disciplineRepository.findByCourseAndSemesterAndSpecialityAndEducationProgram(course, semester, speciality, educationProgram)
                .stream()
                .filter(wasNotPickedForStudent)
                .collect(toList());
    }

    public List<Integer> getStudentCourses(Student student) {
        return extract(student.getDisciplineMarks(), m -> m.getDiscipline().getCourse()).stream().distinct().collect(toList());
    }


    public List<DisciplineMark> alignStudentDisciplinesMark(Student student, List<DisciplineMark> disciplineMarks) {
        List<Discipline> allStudentMagmaynors = extractMagmaynors(student.getDisciplineMarks());
        IntStream.range(0, disciplineMarks.size()).forEach(i -> {
            DisciplineMark disciplineMark = disciplineMarks.get(i);
            Discipline discipline = disciplineMark.getDiscipline();
            if (DisciplineConditions.isMasterDisciplineLabel(discipline.getLabel())) {
                Integer number = Integer.valueOf(discipline.getLabel().split(" ")[1]);
                List<Discipline> magmaynersPerSemester = allStudentMagmaynors.stream()
                        .filter(d -> d.getSemester().equals(discipline.getSemester()))
                        .filter(d -> d.getCourse().equals(discipline.getCourse()))
                        .collect(toList());
                if (number <= magmaynersPerSemester.size())
                    disciplineMark.setDiscipline(magmaynersPerSemester.get(number - 1));
            }
        });
        return disciplineMarks.stream().filter(m -> nonNull(m.getDiscipline().getSemester())).collect(toList());
    }

    public List<DisciplineMark> updateStudentMarks(Student student, List<DisciplineMark> importedMarks) {
        return importedMarks.stream()
                .map(importedMark -> merge(importedMark, student.getDisciplineMarks()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(mark -> ObjectUtils.notEqual(mark.getMark(), mark.getPreviousMark()))
                .collect(toList());
    }

    public <E> List<E> extract(Collection<DisciplineMark> marks, Function<DisciplineMark, E> extractor) {
        return marks.stream().filter(m -> nonNull(m.getDiscipline())).map(extractor).collect(toList());
    }

    private Optional<DisciplineMark> merge(DisciplineMark importedMark, List<DisciplineMark> existingMarks) {
        Function<DisciplineMark, DisciplineMark> mergeExistedAndUpdatedMarks = mark -> {
            mark.setPreviousMark(mark.getMark());
            mark.setMark(importedMark.getMark());
            return mark;
        };
        return getPreviousDisciplineMark(importedMark, existingMarks).map(mergeExistedAndUpdatedMarks);
    }

    private Optional<DisciplineMark> getPreviousDisciplineMark(DisciplineMark importedMark, List<DisciplineMark> existingMarks) {
        return existingMarks.stream().filter(sm -> sm.getDiscipline().getId().equals(importedMark.getDiscipline().getId())).findFirst();
    }

    private List<Discipline> extractMagmaynors(List<DisciplineMark> disciplineMarks) {
        List<Discipline> studentDisciplines = extract(disciplineMarks, DisciplineMark::getDiscipline);
        return studentDisciplines.stream().filter(DisciplineConditions::isMasterDiscipline).collect(toList());
    }
}
