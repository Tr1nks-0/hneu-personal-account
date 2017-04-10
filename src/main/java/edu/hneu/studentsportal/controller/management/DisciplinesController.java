package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.EducationProgram;
import edu.hneu.studentsportal.entity.Faculty;
import edu.hneu.studentsportal.entity.Speciality;
import edu.hneu.studentsportal.enums.DisciplineFormControl;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.FacultyRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Controller
@RequestMapping("/management/disciplines")
public class DisciplinesController {

    @Resource
    private FacultyRepository facultyRepository;
    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;
    @Resource
    private DisciplineRepository disciplineRepository;

    @GetMapping
    public String getDisciplines(@RequestParam(required = false) Long facultyId,
                                 @RequestParam(required = false) Long specialityId,
                                 @RequestParam(required = false) Long educationProgramId,
                                 @RequestParam(required = false, defaultValue = "1") int course,
                                 @RequestParam(required = false, defaultValue = "1") int semester,

                                 Model model) {
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty())
            return "redirect:/management/faculties";

        Faculty faculty = Optional.ofNullable(facultyId).map(facultyRepository::getOne).filter(containsSpecialities()).orElseGet(getFirstFacultyWithSpecialities(faculties));
        if (isNull(faculty))
            return "redirect:/management/specialities";

        List<Speciality> specialities = specialityRepository.findByFaculty(faculty);
        Speciality speciality = Optional.ofNullable(specialityId).map(specialityRepository::getOne).orElse(specialities.get(0));

        EducationProgram educationProgram = Optional.ofNullable(educationProgramId).map(educationProgramRepository::getOne).orElse(null);

        Discipline discipline = new Discipline();
        discipline.setEducationProgram(educationProgram);
        discipline.setSpeciality(speciality);
        discipline.setCourse(course);
        discipline.setSemester(semester);

        model.addAttribute("newDiscipline", discipline);
        model.addAttribute("faculties", faculties);
        model.addAttribute("selectedFaculty", faculty);
        model.addAttribute("specialities", specialities);
        model.addAttribute("educationPrograms", educationProgramRepository.findBySpeciality(speciality));
        model.addAttribute("disciplines", disciplineRepository.findByCourseAndSemesterAndSpecialityAndEducationProgram(course, semester, speciality, educationProgram));
        model.addAttribute("controlForms", DisciplineFormControl.values());
        model.addAttribute("disciplineTypes", DisciplineType.values());
        return "management/disciplines-page";
    }

    private Supplier<Faculty> getFirstFacultyWithSpecialities(List<Faculty> faculties) {
        return () -> faculties.stream().filter(containsSpecialities()).findFirst().orElse(null);
    }

    private Predicate<Faculty> containsSpecialities() {
        return faculty -> isFalse(specialityRepository.findByFaculty(faculty).isEmpty());
    }

    @PostMapping("/create")
    public String createDiscipline(@ModelAttribute @Valid Discipline discipline) {
        disciplineRepository.save(discipline);

        String educationProgramParameter = Optional.ofNullable(discipline.getEducationProgram()).map(EducationProgram::getId)
                .map(id -> "&educationProgramId=" + id).orElse(StringUtils.EMPTY);
        long facultyId = discipline.getSpeciality().getFaculty().getId();
        long specialityId = discipline.getSpeciality().getId();
        return "redirect:/management/disciplines?faculty-id=" + facultyId
                + "&speciality-id=" + specialityId
                + educationProgramParameter
                + "&course=" + discipline.getCourse()
                + "&semester=" + discipline.getSemester();
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public void delete(@PathVariable long id) {
        disciplineRepository.delete(id);
    }

}