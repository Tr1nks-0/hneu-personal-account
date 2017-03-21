package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.service.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/management/students")
public class StudentsController {

    @Resource
    private StudentService studentService;
    @Resource
    private FacultyService facultyService;
    @Resource
    private SpecialityService specialityService;
    @Resource
    private EducationProgramService educationProgramService;
    @Resource
    private GroupService groupService;

    @GetMapping("/{id}")
    public ModelAndView getStudent(@PathVariable long id, Model model) {
        Student student = studentService.getStudent(id);
/*

        if (nonNull(speciality))
            student.setSpeciality(specialityService.getSpeciality(speciality));
        if (nonNull(educationProgram))
            student.setEducationProgram(educationProgramService.getEducationProgram(educationProgram));
        if (nonNull(group))
            student.setStudentGroup(groupService.getGroup(group));
*/



        model.addAttribute("specialities", specialityService.getSpecialitiesForFaculty(student.getFaculty()));
        model.addAttribute("educationPrograms", educationProgramService.getEducationProgramsForSpeciality(student.getSpeciality()));
        model.addAttribute("groups", groupService.getGroups(student.getSpeciality(), student.getEducationProgram()));
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        model.addAttribute("disciplineFormControls", DisciplineFormControl.values());
        return new ModelAndView("management/successfullyUploaded", "student", student);
    }

    @GetMapping("/specialities")
    @ResponseBody
    public List<Speciality> getSpecialities(@RequestParam long id) {
        return specialityService.getSpecialitiesForFaculty(facultyService.getFaculty(id));
    }

    /*@GetMapping("/{id}")
    public String getStudent(@RequestParam Long faculty, @ModelAttribute Student student) {
        student.setFaculty(facultyService.getFaculty(faculty));
        specialityService.getSpecialitiesForFaculty(student.getFaculty()).stream().findFirst().ifPresent(student::setSpeciality);
        Optional.ofNullable(student.getSpeciality()).map(educationProgramService::getEducationProgramsForSpeciality).map(list -> list.stream().findFirst().orElse(null)).ifPresent(student::setEducationProgram);
        if(nonNull(student.getSpeciality()) && nonNull(student.getEducationProgram()))
            groupService.getGroups(student.getSpeciality(), student.getEducationProgram()).stream().findFirst().ifPresent(student::setStudentGroup);
        return "redirect:";
    }*/

    @PostMapping("/{id}")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Success");
        return "redirect:/management/students/" + student.getId();
    }

    @GetMapping("/{id}/img")
    @SneakyThrows
    public void getPhoto(@PathVariable long id, HttpServletResponse response) {
        Student student = studentService.getStudent(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(student.getPhoto());
        outputStream.close();
    }

    @ExceptionHandler(RuntimeException.class)
    public String databaseError() {
        return "management/uploadUserProfilesFromExcel";
    }
}
