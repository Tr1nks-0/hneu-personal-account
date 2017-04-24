package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.service.EducationProgramService;
import edu.hneu.studentsportal.service.GroupService;
import edu.hneu.studentsportal.service.SpecialityService;
import edu.hneu.studentsportal.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@ControllerAdvice
@RequestMapping("/management/students")
public class EditStudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityService specialityService;
    @Resource
    private EducationProgramService educationProgramService;
    @Resource
    private GroupService groupService;

    @GetMapping("/{id}")
    public ModelAndView getStudent(@PathVariable long id, Model model) {
        Student student = studentService.getStudent(id);
        model.addAttribute("specialities", specialityService.getSpecialitiesForFaculty(student.getFaculty()));
        model.addAttribute("educationPrograms", educationProgramService.getEducationProgramsForSpeciality(student.getSpeciality()));
        model.addAttribute("groups", groupService.getGroups(student.getSpeciality(), student.getEducationProgram()));
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        return new ModelAndView("management/studentEditor", "student", student);
    }

    @PostMapping("/{id}")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Success");
        return "redirect:/management/students/" + student.getId();
    }

    @GetMapping("/{id}/remove")
    public String removeStudent(@PathVariable long id) {
        studentService.remove(id);
        return "redirect:/management/import/student";
    }
}
