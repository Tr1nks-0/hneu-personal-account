package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.*;
import edu.hneu.studentsportal.repository.*;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Controller
@RequestMapping("/management/students/{id}/disciplines")
public class StudentDisciplinesController {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private DisciplineMarkRepository disciplineMarkRepository;

    @GetMapping
    public String getStudentMarks(@PathVariable("id") long studentId, Model model,
                                  @RequestParam(defaultValue = "1") int course, @RequestParam(defaultValue = "1") int semester) {
        Student student = studentRepository.findOne(studentId);
        DisciplineMark newMark = new DisciplineMark();
        newMark.setStudent(student);
        return prepareStudentEditorPage(model, student, newMark, course, semester);
    }

    @PostMapping
    public String createDisciplineMark(@ModelAttribute @Valid DisciplineMark disciplineMark,
                                       @PathVariable("id") long studentId,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Discipline discipline = disciplineMark.getDiscipline();
        if(bindingResult.hasErrors()) {
            Student student = studentRepository.findOne(studentId);
            return prepareStudentEditorPage(model, student, disciplineMark, discipline.getCourse(), discipline.getSemester());
        } else {
            if (isNull(disciplineMark.getId()))
                redirectAttributes.addFlashAttribute("success", "success.add.discipline");
            else
                redirectAttributes.addFlashAttribute("success", "success.update.discipline");
            disciplineMarkRepository.save(disciplineMark);
            redirectAttributes.addAttribute("course", discipline.getCourse());
            redirectAttributes.addAttribute("semester", discipline.getSemester());
            return "redirect:/management/students/" + studentId + "/disciplines";
        }
    }

    @PostMapping("/{discipline-id}/delete")
    @ResponseBody
    public void delete(@PathVariable("discipline-id") long disciplineId) {
        disciplineMarkRepository.delete(disciplineId);
    }

    @GetMapping("/{discipline-id}")
    public String getStudentDiscipline(@PathVariable("id") long studentId, @PathVariable("discipline-id") long disciplineId, Model model) {
        Student student = studentRepository.findOne(studentId);
        DisciplineMark disciplineMark = disciplineMarkRepository.findOne(disciplineId);
        if(isNull(disciplineMark))
            return "redirect:/management/students/" + studentId + "/disciplines";
        Discipline discipline = disciplineMark.getDiscipline();
        return prepareStudentEditorPage(model, student, disciplineMark, discipline.getCourse(), discipline.getSemester());
    }

    private String prepareStudentEditorPage(Model model, Student student, DisciplineMark mark, int defaultCourse, int defaultSemester) {
        Optional<Discipline> discipline = Optional.ofNullable(mark.getDiscipline());
        int course = discipline.map(Discipline::getCourse).orElse(defaultCourse);
        int semester = discipline.map(Discipline::getSemester).orElse(defaultSemester);
        List<DisciplineMark> disciplineMarks = getStudentMarks(student, course, semester);
        List<Discipline> possibleDisciplines = getPossibleDisciplinesForStudent(student, course, semester, disciplineMarks);
        model.addAttribute("disciplineMark", mark);
        model.addAttribute("disciplines", possibleDisciplines);
        model.addAttribute("marks", disciplineMarks);
        model.addAttribute("student", student);
        populateCoursesAndSemesters(model, student, course, semester);
        return "management/student-disciplines-editor-page";
    }

    private void populateCoursesAndSemesters(Model model, Student student, int course, int semester) {
        List<Integer> courses = student.getDisciplineMarks().stream().map(DisciplineMark::getDiscipline).map(Discipline::getCourse).distinct().collect(Collectors.toList());
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedSemester", semester);
    }

    private List<DisciplineMark> getStudentMarks(Student student, int course, int semester) {
        Predicate<DisciplineMark> hasGivenCourse = m -> m.getDiscipline().getCourse() == course;
        Predicate<DisciplineMark> hasGivenSemester = m -> m.getDiscipline().getSemester() == semester;
        return student.getDisciplineMarks().stream().filter(hasGivenCourse.and(hasGivenSemester)).collect(Collectors.toList());
    }

    private List<Discipline> getPossibleDisciplinesForStudent(Student student, int course, int semester, List<DisciplineMark> studentMarks) {
        List<Discipline> exceptDisciplines = studentMarks.stream().map(DisciplineMark::getDiscipline).collect(Collectors.toList());
        Speciality speciality = student.getSpeciality();
        EducationProgram educationProgram = student.getEducationProgram();
        Predicate<Discipline> wasNotPickedForStudent = discipline -> isFalse(exceptDisciplines.contains(discipline));
        return disciplineRepository.findByCourseAndSemesterAndSpecialityAndEducationProgram(course, semester, speciality, educationProgram)
                .stream()
                .filter(wasNotPickedForStudent)
                .collect(Collectors.toList());
    }
}
