package edu.hneu.studentsportal.service.impl;


import edu.hneu.studentsportal.dao.GroupDao;
import edu.hneu.studentsportal.dao.StudentDao;
import edu.hneu.studentsportal.model.*;
import edu.hneu.studentsportal.parser.PointsExcelParser;
import edu.hneu.studentsportal.parser.StudentProfileExcelParser;
import edu.hneu.studentsportal.parser.dto.PointsDto;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.*;

import static java.util.Objects.nonNull;

@Service
public class DefaultStudentService implements StudentService {

    private static final Logger LOG = Logger.getLogger(DefaultStudentService.class);

    private static final int PREFIX_LENGTH = 5;
    private static final double MIN_SIMILARITY_COEFFICIENT = 0.6;
    private static final Map<String, String> semesters = new HashMap<>();
    public static final String GET_STUDENT_EMAIL_URL = "http://212.111.199.46:65008/EmailToOutController?name=%s&surname=%s&groupId=%s";

    static {
        semesters.put("1", "І СЕМЕСТР");
        semesters.put("2", "ІІ СЕМЕСТР");
        semesters.put("3", "ІІІ СЕМЕСТР");
        semesters.put("4", "IV СЕМЕСТР");
        semesters.put("5", "V СЕМЕСТР");
        semesters.put("6", "VІ СЕМЕСТР");
        semesters.put("7", "VІI СЕМЕСТР");
    }

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private GroupDao groupDao;

    @Override
    public StudentProfile readStudentProfilesFromFile(File file) {
        return ((StudentProfileExcelParser) context.getBean("studentProfileExcelParser")).parse(file);
    }

    @Override
    public void save(StudentProfile studentProfile) {
        studentDao.remove(studentProfile.getId());
        studentDao.save(studentProfile);
    }

    @Override
    public StudentProfile findStudentProfileById(String id) {
        return studentDao.findById(id);
    }

    @Override
    public Optional<StudentProfile> findStudentProfileByEmail(String email) {
        return studentDao.findByEmail(email);
    }

    public void updateStudentsScoresFromFile(File file) {
        PointsDto studentsPoints = new PointsExcelParser().parse(file);
        for (Map.Entry<String, Map<String, String>> studentScore : studentsPoints.getMap().entrySet()) {
            StudentProfile studentProfile = findStudentProfileById(studentScore.getKey());
            if (nonNull(studentProfile)) {
                String semesterId = studentsPoints.getSemester();
                updateStudentProfileSemester(studentProfile, semesterId, studentScore.getValue());
                save(studentProfile);
            }
        }
    }

    private void updateStudentProfileSemester(StudentProfile studentProfile, String semesterId, Map<String, String> studentScore) {
        Optional<Semester> semester = findSemesterForLabel(studentProfile, semesterId);
        if (semester.isPresent()) {
            List<Discipline> disciplines = semester.get().getDisciplines();
            for (Discipline discipline : disciplines)
                discipline.setMark(getDisciplineScore(studentScore, discipline.getLabel()));
            if (!studentScore.isEmpty()) {
                for (Map.Entry<String, String> entry : studentScore.entrySet()) {
                    Discipline discipline = getEmptyDiscipline(disciplines);
                    discipline.setLabel(entry.getKey());
                    discipline.setMark(entry.getValue());
                    disciplines.add(discipline);
                }
            }
        }
    }

    private Discipline getEmptyDiscipline(List<Discipline> disciplines) {
        return disciplines.stream()
                .filter(discipline -> StringUtils.isEmpty(discipline.getLabel()))
                .findFirst()
                .orElse(new Discipline());
    }

    private Optional<Semester> findSemesterForLabel(StudentProfile studentProfile, String semesterId) {
        for (Course course : studentProfile.getCourses())
            for (Semester semester : course.getSemesters())
                if (Objects.equals(semesters.get(semesterId), semester.getLabel()))
                    return Optional.of(semester);
        return Optional.empty();
    }

    private String getDisciplineScore(Map<String, String> studentScore, String discipline) {
        for (String disciplineName : studentScore.keySet()) {
            if (isAppropriateDisciplineName(discipline, disciplineName)) {
                String score = studentScore.get(disciplineName);
                studentScore.remove(disciplineName);
                return score;
            }
        }
        return null; // should be empty in case when discipline mark wasn't found.
    }

    private boolean isAppropriateDisciplineName(String discipline, String total) {
        int levenshteinDistance = StringUtils.getLevenshteinDistance(discipline, total);
        double antiSimilarityCoefficient = levenshteinDistance * 1.0 / Math.max(discipline.length(), total.length());
        return antiSimilarityCoefficient < MIN_SIMILARITY_COEFFICIENT && total.startsWith(discipline.substring(0, PREFIX_LENGTH))
                || total.startsWith(discipline.substring(0, discipline.length() / 3));
    }

    @Override
    public List<StudentProfile> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void setCredentials(StudentProfile studentProfile) {
        String studentEmail = getStudentEmail(studentProfile);
        if (StringUtils.isNotBlank(studentEmail)) {
            User user = new User();
            user.setId(studentEmail);
            studentProfile.setEmail(studentEmail);
            user.setPassword("0000");
            //studentProfile.setPassword(UUID.randomUUID().toString().substring(0, 8));
            user.setRole(2);
            userService.save(user);
        } else {
            throw new RuntimeException("Cannot found email for the user!");
        }
    }

    @Override
    public void setGroupId(StudentProfile studentProfile) {
        Group group = groupDao.findOne(studentProfile.getGroup());
        studentProfile.setGroupId(group.getId());
    }

    private String getStudentEmail(StudentProfile studentProfile) {
        try {
            String name = studentProfile.getName().toLowerCase().split(" ")[0];
            String surname = studentProfile.getSurname().toLowerCase();
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate
                    .getForEntity(String.format(GET_STUDENT_EMAIL_URL, name, surname, studentProfile.getGroup()), String.class)
                    .getBody();
        } catch (RuntimeException e) {
            LOG.warn("Cannot receive the email!", e);
            return StringUtils.EMPTY;
        }

    }
}
