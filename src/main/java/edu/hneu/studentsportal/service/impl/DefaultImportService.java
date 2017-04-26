package edu.hneu.studentsportal.service.impl;


import edu.hneu.studentsportal.entity.DisciplineMark;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.parser.factory.ParserFactory;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.ImportService;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Log4j
@Service
public class DefaultImportService implements ImportService {

    private static final String ID_REGEX = "id=\".+\"";
    private static final String NAME_REGEX = "<displayName>.+</displayName>";
    private static final String FACULTIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=faculties&auth=test";
    private static final String SPECIALITIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=specialities&facultyid=%s&auth=test";
    private static final String GROUPS_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=groups&facultyid=%s&specialityid=%s&course=%s&auth=test";

    @Resource
    private ParserFactory parserFactory;
    @Resource
    private UserService userService;
    @Resource
    private StudentService studentService;
    @Resource
    private GroupRepository groupRepository;
    @Value("${emails.integration.service.url}")
    public String emailsIntegrationServiceUrl;


    @Override
    public Student importStudent(File file) {
        Student student = parserFactory.newStudentProfileExcelParser().parse(file);
        //student.setEmail(retrieveStudentEmailFromThirdPartyService(student));
        student.setEmail(student.getSurname() + "@hneu.net");
        userService.create(student.getEmail(), UserRole.STUDENT);
        studentService.save(student);
        log.info(format("New %s has been created.", student));
        return student;
    }

    @Override
    public Set<Student> importStudentMarks(File file) {
        Map<Student, List<DisciplineMark>> importedStudentsMarks = parserFactory.newStudentMarksExcelParser().parse(file);
        importedStudentsMarks.forEach((student, importedMarks) -> {
            importedMarks.forEach(merge(student.getDisciplineMarks()));
            studentService.save(student);
        });
        return importedStudentsMarks.keySet();
    }

    private Consumer<DisciplineMark> merge(List<DisciplineMark> existingMarks) {
        return importedMark -> {
            DisciplineMark studentDiscipline = alignWithExisting(importedMark, existingMarks);
            studentDiscipline.setMark(importedMark.getMark());
        };
    }

    private DisciplineMark alignWithExisting(DisciplineMark importedMark, List<DisciplineMark> existingMarks) {
        return existingMarks.stream()
                .filter(sm -> sm.getDiscipline().equals(importedMark.getDiscipline()))
                .findFirst().orElse(importedMark);
    }

    private String retrieveStudentEmailFromThirdPartyService(Student student) {
        String formattedName = student.getName().toLowerCase().split(" ")[0];
        String formatterSurname = student.getSurname().toLowerCase().trim();
        String groupName = student.getGroup().getName();
        String url = format("%s/EmailToOutController?name=%s&surname=%s&groupId=%s", emailsIntegrationServiceUrl, formattedName, formatterSurname, groupName);
        return new RestTemplate().getForEntity(url, String.class).getBody().toLowerCase();
    }


    @Override
    public void downloadGroups() {
        log.info("Groups downloading started");
        /*final List<String> facultyIds = getListOfIds(getStringResponse(FACULTIES_URL));
        for(final String facultyId : facultyIds) {
            final List<String> specialityIds =  getListOfIds(getStringResponse(String.format(SPECIALITIES_URL, facultyId)));
            for(final String specialityId : specialityIds) {
                for(int course = 1; course <= 6; course++) {
                    final String response = getStringResponse(String.format(GROUPS_URL, facultyId, specialityId, course));
                    final List<String> groupIds = getListOfIds(response);
                    final List<String> groupName = getListOfNames(response);
                    for (int i = 0; i < groupIds.size(); i++) {
                        groupRepository.save(new Group(Integer.valueOf(groupIds.get(i)), groupName.get(i)));
                    }
                }
            }
        }*/
        log.info("Groups downloading finished");
    }

    private String getStringResponse(final String url) {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    private List<String> getListOfIds(final String stringToParse) {
        final Matcher matcher = Pattern.compile(ID_REGEX).matcher(stringToParse);
        final List<String> ids = new ArrayList<>();
        while(matcher.find()) {
            final String parsedId = matcher.group();
            final String idValue = parsedId.substring(4, parsedId.length() - 1);
            ids.add(idValue);
        }
        return ids;
    }

    private List<String> getListOfNames(final String stringToParse) {
        final Matcher matcher = Pattern.compile(NAME_REGEX).matcher(stringToParse);
        final List<String> names = new ArrayList<>();
        while(matcher.find()) {
            final String parsedName = matcher.group();
            final String nameValue = parsedName.substring(13, parsedName.length() - 14);
            names.add(nameValue);
        }
        return names;
    }


}
