package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.*;
import edu.hneu.studentsportal.domain.dto.StudentDTO;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import edu.hneu.studentsportal.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.CREATE_STUDENTS_URL;
import static java.util.Objects.nonNull;

@Log4j
@Controller
@RequestMapping(CREATE_STUDENTS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateStudentManagementController extends AbstractManagementController {

    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;
    private final EducationProgramRepository educationProgramRepository;
    private final GroupRepository groupRepository;
    private final StudentService studentService;

    @GetMapping
    public String createStudent(Model model,
                                @RequestParam(required = false) Long facultyId,
                                @RequestParam(required = false) Long specialityId,
                                @RequestParam(required = false) Long educationProgramId) throws URISyntaxException {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty()) {
            return "redirect:/management/faculties";
        }

        Faculty faculty = facultyRepository.findById(facultyId).orElse(faculties.get(0));
        List<Speciality> specialities = specialityRepository.findAllByFacultyId(faculty.getId());
        if (specialities.isEmpty()) {
            return "redirect:/management/specialities?facultyId=" + faculty.getId();
        }

        Speciality speciality = specialityRepository.findById(specialityId).orElse(specialities.get(0));
        EducationProgram educationProgram = educationProgramRepository.findById(educationProgramId);
        List<Group> groups = groupRepository.findBySpecialityAndEducationProgram(speciality, educationProgram);
        if (groups.isEmpty() && nonNull(educationProgram)) {
            return new URIBuilder("redirect:/management/groups")
                    .addParameter("facultyId", String.valueOf(faculty.getId()))
                    .addParameter("specialityId", String.valueOf(speciality.getId()))
                    .addParameter("educationProgramId", String.valueOf(educationProgram.getId()))
                    .toString();
        }

        return prepareStudentPage(model, StudentDTO.builder()
                .faculty(faculty)
                .speciality(speciality)
                .educationProgram(educationProgram)
                .build());
    }

    @PostMapping
    public String createStudent(@ModelAttribute @Valid StudentDTO student, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return prepareStudentPage(model, student);
        } else {
            Student newStudent = studentService.createStudent(student);
            redirectAttributes.addFlashAttribute("success", "success.add.student");
            return "redirect:/management/students/" + newStudent.getId();
        }
    }

    private String prepareStudentPage(Model model, StudentDTO student) {
        Faculty faculty = student.getFaculty();
        Speciality speciality = student.getSpeciality();
        EducationProgram educationProgram = student.getEducationProgram();
        model.addAttribute("faculties", facultyRepository.findAll());
        model.addAttribute("specialities", specialityRepository.findAllByFacultyId(faculty.getId()));
        model.addAttribute("educationPrograms", educationProgramRepository.findAllBySpeciality(speciality));
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("selectedSpeciality", speciality);
        model.addAttribute("selectedEducationProgram", educationProgram);
        model.addAttribute("groups", groupRepository.findBySpecialityAndEducationProgram(speciality, educationProgram));
        model.addAttribute("student", student);
        model.addAttribute("title", "management-create-student");
        return "management/create-student-page";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected String handleError(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, e.getMessage(), redirectAttributes);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleError(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        return handleErrorInternal(e, messageService.studentExistsError(), redirectAttributes);
    }

    @Override
    public String baseUrl() {
        return CREATE_STUDENTS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }

}