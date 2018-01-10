package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.*;
import edu.hneu.studentsportal.service.DisciplineMarkService;
import edu.hneu.studentsportal.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_STUDENTS_URL;

@Log4j
@Controller
@RequestMapping(MANAGE_STUDENTS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentsManagementController extends AbstractManagementController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;
    private final EducationProgramRepository educationProgramRepository;
    private final GroupRepository groupRepository;
    private final DisciplineMarkService disciplineMarkService;

    @GetMapping("/{id}")
    public String getStudent(@PathVariable long id, Model model) {
        Student student = studentRepository.findOne(id);
        return prepareStudentEditorPage(model, student);
    }

    @PostMapping("/{id}")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareStudentEditorPage(model, student);
        } else {
            studentService.save(student);
            log.info(String.format("New [%s] has been added", student.toString()));
            redirectAttributes.addFlashAttribute("success", "success.save.student");
            return "redirect:/management/students/" + student.getId();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void deleteStudent(@PathVariable long id) {
        studentRepository.delete(id);
        log.warn(String.format("Student[%s] has been deleted", id));
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

    private String prepareStudentEditorPage(Model model, Student student) {
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(student.getFaculty().getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(student.getSpeciality()));
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(student.getSpeciality(), student.getEducationProgram()));
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        model.addAttribute("student", student);
        model.addAttribute("courses", disciplineMarkService.getCourses(student));
        model.addAttribute("title", "management-students");
        return "management/student-editor-page";
    }
}
