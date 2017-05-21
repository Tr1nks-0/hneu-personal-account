package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.repository.DisciplineMarkRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.impl.StudentDisciplineMarksServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Log4j
@Controller
@RequestMapping("/management/students/{id}/disciplines")
public class StudentDisciplinesController {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private DisciplineMarkRepository disciplineMarkRepository;
    @Resource
    private StudentDisciplineMarksServiceImpl studentDisciplineMarksService;

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
        if (bindingResult.hasErrors()) {
            Student student = studentRepository.findOne(studentId);
            return prepareStudentEditorPage(model, student, disciplineMark, discipline.getCourse(), discipline.getSemester());
        } else {
            String successMessage = isNull(disciplineMark.getId()) ? "success.add.discipline" : "success.update.discipline";
            redirectAttributes.addFlashAttribute("success", successMessage);
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
        if (nonNull(disciplineMark)) {
            Discipline discipline = disciplineMark.getDiscipline();
            return prepareStudentEditorPage(model, student, disciplineMark, discipline.getCourse(), discipline.getSemester());
        } else {
            return "redirect:/management/students/" + studentId + "/disciplines";
        }
    }

    private String prepareStudentEditorPage(Model model, Student student, DisciplineMark mark, int defaultCourse, int defaultSemester) {
        Optional<Discipline> discipline = Optional.ofNullable(mark.getDiscipline());
        int course = discipline.map(Discipline::getCourse).orElse(defaultCourse);
        int semester = discipline.map(Discipline::getSemester).orElse(defaultSemester);
        model.addAttribute("disciplineMark", mark);
        model.addAttribute("disciplines", studentDisciplineMarksService.getPossibleNewDisciplinesForStudent(student, course, semester));
        model.addAttribute("marks", studentDisciplineMarksService.getStudentMarks(student, course, semester));
        model.addAttribute("student", student);
        model.addAttribute("selectedCourse", course);
        model.addAttribute("selectedSemester", semester);
        model.addAttribute("courses", studentDisciplineMarksService.getStudentCourses(student));
        return "management/student-disciplines-editor-page";
    }

}
