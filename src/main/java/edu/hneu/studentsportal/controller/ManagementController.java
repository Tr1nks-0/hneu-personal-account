package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.entity.*;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.pojo.FilesUploadModel;
import edu.hneu.studentsportal.pojo.StudentDiscipline;
import edu.hneu.studentsportal.pojo.StudentDisciplines;
import edu.hneu.studentsportal.service.FileService;
import edu.hneu.studentsportal.service.ScheduleService;
import edu.hneu.studentsportal.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/management")
public class ManagementController {

    private static final Logger LOG = Logger.getLogger(ManagementController.class.getName());
    private static final Comparator<StudentDiscipline> STUDENT_DISCIPLINE_COMPARATOR = (d1, d2) -> {
        if (d1.getSurname().equals(d2.getSurname())) {
            return d1.getName().compareTo(d2.getName());
        }
        return d1.getSurname().compareTo(d2.getSurname());
    };

    @Autowired
    private StudentService studentService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private FileService fileService;
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/uploadStudentProfilesFromExcel")
    public String addUserProfilesFromExcel() {
        return "management/uploadUserProfilesFromExcel";
    }

    @RequestMapping(value = "/uploadStudentProfilesFromExcel", method = RequestMethod.POST)
    public String uploadFiles(@ModelAttribute("uploadForm") final FilesUploadModel uploadForm,
                              final RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        LOG.info("Files are going to upload");
        final List<MultipartFile> filesToUpload = uploadForm.getFiles();
        final Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload, uploadedFile -> {
            final Student studentProfile = studentService.readStudentProfilesFromFile(uploadedFile);
            studentService.setCredentials(studentProfile);
            studentService.save(studentProfile);
            studentService.sendEmailAfterProfileCreation(studentProfile);
        });
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        LOG.info("Files were uploaded");
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel", method = RequestMethod.POST)
    public String uploadTotalsFromExcel(@ModelAttribute("uploadForm") final FilesUploadModel uploadForm,
                                        final RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
        final List<MultipartFile> filesToUpload = uploadForm.getFiles();
        final Map<String, Boolean> uploadedFilesNames = fileService.reduceForEachUploadedFile(filesToUpload, uploadedFile -> {
            studentService.updateStudentsScoresFromFile(uploadedFile);
            studentService.refreshMarks();
        });
        redirectAttributes.addFlashAttribute("files", uploadedFilesNames);
        return "redirect:successfullyUploaded";
    }

    @RequestMapping(value = "/uploadTotalsFromExcel")
    public String uploadTotalsFromExcel() {
        return "management/uploadTotalsFromExcel";
    }

    @RequestMapping(value = "/successfullyUploaded")
    public String successfullyUploaded() {
        return "management/successfullyUploaded";
    }

    @RequestMapping(value = "/synchronizeSchedule")
    public String synchronizeSchedulePage() {
        return "management/synchronizeSchedule";
    }

    @RequestMapping(value = "/students")
    public String studentsListPage(@RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "search-criteria", required = false) final String searchCriteria,
                                   final Model model) {
        if (isNull(page))
            page = 1;
        model.addAttribute("page", page);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("pagesCount", studentService.getPagesCountForSearchCriteria(searchCriteria));
        model.addAttribute("students", studentService.find(searchCriteria, page));
        return "management/studentsList";
    }

    @RequestMapping(value = "/special-disciplines")
    public String specialDisciplines(@RequestParam(value = "group", required = false) final String group,
                                   final Model model) {
        model.addAttribute("group", group);
        if(nonNull(group) && !group.isEmpty()) {
            model.addAttribute("groups", studentService.find().stream()
                    .map(Student::getStudentGroup)
                    .filter(userGroup -> userGroup.getName().contains(group))
                    .sorted()
                    .collect(Collectors.toSet()));

        }else {
            model.addAttribute("groups",
                    studentService.find().stream()
                            .map(Student::getStudentGroup)
                            .sorted()
                            .collect(Collectors.toSet()));
        }
        return "management/groupsList";
    }

    @RequestMapping(value = "/groups/{group:.+}")
    public String choseSemesterForSpecialDisciplines(@PathVariable final String group, final Model model) {
        model.addAttribute("group", group);
        final Optional<Student> studentProfile = studentService.find().stream()
                .filter(student -> student.getStudentGroup().getName().contains(group)).findAny();
        model.addAttribute("coursesCount", studentProfile.map(profile -> profile.getCourses().size()).orElse(0));
        return "management/semesterPerGroup";
    }

    @RequestMapping(value = "/groups/{group:.+}/{course}/{semester}")
    public ModelAndView studentListForSpecialDisciplines(@PathVariable final String group,
                                                         @PathVariable Integer course,
                                                         @PathVariable Integer semester, final Model model) {
        model.addAttribute("group", group);
        model.addAttribute("course", course);
        model.addAttribute("semester", semester);
        model.addAttribute("maynors", new StudentDisciplines(getSpecialDisciplines(group, --course, --semester, DisciplineType.MAYNOR)));
        return new ModelAndView("management/specialDisciplines");
    }

    @RequestMapping(value = "/maynors/{group:.+}/{course}/{semester}", method = RequestMethod.POST)
    public String studentListForSpecialDisciplinesPost(@PathVariable final String group, @PathVariable final Integer course,
                                                       @PathVariable final Integer semester, final Model model,
                                                       @ModelAttribute final StudentDisciplines disciplines) {
        final int courseNumber = course - 1;
        final int semesterNumber = semester - 1;
        disciplines.getList().forEach(discipline -> {
            final Student studentProfile = studentService.findStudentProfileById(discipline.getStudentId());
            final Discipline studentDiscipline = studentProfile.getCourses().get(courseNumber)
                    .getSemesters().get(semesterNumber)
                    .getDisciplines().get(discipline.getNumber());
            studentDiscipline.setLabel(discipline.getLabel());
            studentDiscipline.setMark(discipline.getMark());
            studentService.save(studentProfile);
        });
        return String.format("redirect:/management/groups/%s/%s/%s", group, course, semester);
    }

    private List<StudentDiscipline> getSpecialDisciplines(final String group, final Integer course, final Integer semester, final DisciplineType type) {
        final List<StudentDiscipline> disciplines = studentService.find().stream()
                .filter(student -> student.getStudentGroup().getName().contains(group))
                .map(student -> {
                    Course courseModel = student.getCourses().get(course);
                    Semester semesterModel = courseModel.getSemesters().get(semester);
                    return IntStream.range(0, semesterModel.getDisciplines().size())
                            .filter(i -> type.equals(semesterModel.getDisciplines().get(i).getType()))
                            .mapToObj(i -> new StudentDiscipline(
                                    student.getEmail(),
                                    student.getName(),
                                    student.getSurname(),
                                    i,
                                    semesterModel.getDisciplines().get(i)
                                )
                            ).collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Collections.sort(disciplines, STUDENT_DISCIPLINE_COMPARATOR);
        return disciplines;
    }


    @RequestMapping(value = "/students/{id:.+}")
    public ModelAndView editStudent(@PathVariable("id") final String id) {
        final Student studentProfile = studentService.findStudentProfileById(id);
        return new ModelAndView("management/studentEditor", "profile", studentProfile);
    }

    @RequestMapping(value = "/students/{id:.+}", method = RequestMethod.POST)
    public String editStudent(@PathVariable("id") final String id, @ModelAttribute("profile") final Student editedProfile) {
        final Student studentProfile = studentService.findStudentProfileById(id);
        if (nonNull(studentProfile)){
            studentProfile.setName(editedProfile.getName());
            studentProfile.setSurname(editedProfile.getSurname());
            studentProfile.setFaculty(editedProfile.getFaculty());
            studentProfile.setSpeciality(editedProfile.getSpeciality());
            studentProfile.setStudentGroup(editedProfile.getStudentGroup());
            studentProfile.setContactInfo(editedProfile.getContactInfo());
            studentService.save(studentProfile);
        }
        return "redirect:/management/students";
    }

    @RequestMapping(value = "/removeStudent")
    public String removeStudent(@RequestParam final String id) {
        studentService.remove(id);
        return "redirect:students";
    }

    @RequestMapping(value = "/downloadGroups")
    public String downloadGroups() {
        Executors.newSingleThreadExecutor().execute( () -> scheduleService.downloadGroups());
        return "redirect:scheduleSynchronizedSuccessfully";
    }

    @RequestMapping(value = "/scheduleSynchronizedSuccessfully")
    public String groupsDownloadedSuccessfully() {
        return "management/scheduleSynchronizedSuccessfully";
    }

}