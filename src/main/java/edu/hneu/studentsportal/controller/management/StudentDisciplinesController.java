package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.controller.ExceptionHandlingController;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.DisciplineMarkRepository;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_STUDENT_DISCIPLINES_URL;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.ListUtils.union;

@Log4j
@Controller
@RequestMapping(MANAGE_STUDENT_DISCIPLINES_URL)
public class StudentDisciplinesController implements ExceptionHandlingController {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private DisciplineMarkRepository disciplineMarkRepository;
    @Resource
    private DisciplineMarkService studentDisciplineMarksService;
    @Resource
    private MessageService messageService;

    @GetMapping
    public String getStudentMarks(@PathVariable long studentId, Model model,
                                  @RequestParam(defaultValue = "1") int course, @RequestParam(defaultValue = "1") int semester) {
        Student student = studentRepository.findOne(studentId);
        DisciplineMark newMark = new DisciplineMark();
        return prepareStudentEditorPage(model, student, newMark, course, semester);
    }

    @PostMapping
    public String createDisciplineMark(@ModelAttribute @Valid DisciplineMark disciplineMark,
                                       @PathVariable long studentId,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Discipline discipline = disciplineMark.getDiscipline();
        Student student = studentRepository.findOne(studentId);
        if (bindingResult.hasErrors()) {
            return prepareStudentEditorPage(model, student, disciplineMark, discipline.getCourse(), discipline.getSemester());
        } else if (isNull(disciplineMark.getId())) {
            student.setDisciplineMarks(union(student.getDisciplineMarks(), asList(disciplineMark)));
            studentRepository.save(student);
            redirectAttributes.addFlashAttribute("success", "success.add.discipline");
        } else {
            disciplineMarkRepository.save(disciplineMark);
            redirectAttributes.addFlashAttribute("success", "success.update.discipline");
        }
        redirectAttributes.addAttribute("course", discipline.getCourse());
        redirectAttributes.addAttribute("semester", discipline.getSemester());
        return "redirect:/management/students/" + studentId + "/disciplines";
    }

    @PostMapping("/{disciplineId}/delete")
    @ResponseBody
    public void delete(@PathVariable long studentId, @PathVariable long disciplineId) {
        Student student = studentRepository.findOne(studentId);
        List<DisciplineMark> disciplineMarks = student.getDisciplineMarks();
        disciplineMarks.remove(disciplineMarkRepository.findOne(disciplineId));
        student.setDisciplineMarks(disciplineMarks);
        studentRepository.save(student);
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

    @ExceptionHandler(TransactionSystemException.class)
    public String handleError(TransactionSystemException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e.getCause(), messageService.semesterDisciplinesLimitExceededError(), redirectAttributes);
    }

    @Override
    public String baseUrl() {
        return MANAGE_STUDENT_DISCIPLINES_URL;
    }

    @Override
    public Logger logger() {
        return log;
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
        model.addAttribute("title", "management-student-disciplines");
        return "management/student-disciplines-editor-page";
    }
}
