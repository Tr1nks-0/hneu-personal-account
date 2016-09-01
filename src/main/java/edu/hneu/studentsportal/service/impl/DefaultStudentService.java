package edu.hneu.studentsportal.service.impl;


import static java.util.Objects.nonNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.ImmutableMap;

import edu.hneu.studentsportal.dao.GroupDao;
import edu.hneu.studentsportal.dao.StudentDao;
import edu.hneu.studentsportal.model.Course;
import edu.hneu.studentsportal.model.Discipline;
import edu.hneu.studentsportal.model.Group;
import edu.hneu.studentsportal.model.Semester;
import edu.hneu.studentsportal.model.StudentProfile;
import edu.hneu.studentsportal.model.User;
import edu.hneu.studentsportal.parser.PointsExcelParser;
import edu.hneu.studentsportal.parser.StudentProfileExcelParser;
import edu.hneu.studentsportal.parser.dto.PointsDto;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;

@Service
public class DefaultStudentService implements StudentService {

    private static final Logger LOG = Logger.getLogger(DefaultStudentService.class);

    private static final int PREFIX_LENGTH = 5;
    private static final double MIN_SIMILARITY_COEFFICIENT = 0.6;
    private static final Map<String, String> semesters = new HashMap<>();
    private static final String GET_STUDENT_EMAIL_URL = "/EmailToOutController?name=%s&surname=%s&groupId=%s";
    private static final String SEND_PASSWORD_VM_TEMPLATE = "velocity/sendProfileWasCreatedMessageWithPassword.vm";
    private static final String SEND_PROFILE_WAS_CHANGED_VM_TEMPLATE = "velocity/sendProfileWasChangedMessage.vm";

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
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private DefaultEmailService emailService;

    @Value("${support.mail}")
    public String supportMail;
    @Value("${emails.integration.service.url}")
    public String emailsIntegrationServiceUrl;

    @Override
    public StudentProfile readStudentProfilesFromFile(final File file) {
        LOG.info("Create new profile from : " + file.getAbsolutePath());
        return ((StudentProfileExcelParser) context.getBean("studentProfileExcelParser")).parse(file);
    }

    @Override
    public void save(final StudentProfile studentProfile) {
        studentDao.remove(studentProfile.getId());
        studentDao.save(studentProfile);
    }

    @Override
    public StudentProfile findStudentProfileById(final String id) {
        return studentDao.findById(id);
    }

    @Override
    public Optional<StudentProfile> findStudentProfileByEmail(final String email) {
        return studentDao.findByEmail(email);
    }

    public void updateStudentsScoresFromFile(final File file) {
        final PointsDto studentsPoints = new PointsExcelParser().parse(file);
        for (final Map.Entry<String, Map<String, String>> studentScore : studentsPoints.getMap().entrySet()) {
            final StudentProfile studentProfile = getStudentProfile(studentScore);
            if (nonNull(studentProfile)) {
                final String semesterId = studentsPoints.getSemester();
                updateStudentProfileSemester(studentProfile, semesterId, studentScore.getValue());
                save(studentProfile);
                sendEmailAfterProfileUpdating(studentProfile);
            }
        }
    }

    private StudentProfile getStudentProfile(final Map.Entry<String, Map<String, String>> studentScore) {
        final String[] keys = studentScore.getKey().split("\\$");
        if(keys.length == 2) {
            final String subKey = keys[0];
            final String groupCode = keys[1];
            return findStudentProfile(subKey, groupCode);
        }
        return null;
    }

    private StudentProfile findStudentProfile(final String subKey, final String groupCode) {
        return studentDao.find(subKey, groupCode);
    }

    private void updateStudentProfileSemester(final StudentProfile studentProfile, final String semesterId, final Map<String, String> studentScore) {
        final Optional<Semester> semester = findSemesterForLabel(studentProfile, semesterId);
        if (semester.isPresent()) {
            final List<Discipline> disciplines = semester.get().getDisciplines();
            for (final Discipline discipline : disciplines)
                discipline.setMark(getDisciplineScore(studentScore, discipline.getLabel()));
            if (!studentScore.isEmpty()) {
                for (final Map.Entry<String, String> entry : studentScore.entrySet()) {
                    final Discipline discipline = getEmptyDiscipline(disciplines);
                    discipline.setLabel(entry.getKey());
                    discipline.setMark(entry.getValue());
                    disciplines.add(discipline);
                }
            }
        }
    }

    private Discipline getEmptyDiscipline(final List<Discipline> disciplines) {
        return disciplines.stream()
                .filter(discipline -> StringUtils.isEmpty(discipline.getLabel()))
                .findFirst()
                .orElse(new Discipline());
    }

    private Optional<Semester> findSemesterForLabel(final StudentProfile studentProfile, final String semesterId) {
        for (final Course course : studentProfile.getCourses())
            for (final Semester semester : course.getSemesters())
                if (Objects.equals(semesters.get(semesterId), semester.getLabel()))
                    return Optional.of(semester);
        return Optional.empty();
    }

    private String getDisciplineScore(final Map<String, String> studentScore, final String discipline) {
        for (final String disciplineName : studentScore.keySet()) {
            if (isAppropriateDisciplineName(discipline, disciplineName)) {
                final String score = studentScore.get(disciplineName);
                studentScore.remove(disciplineName);
                return score;
            }
        }
        return null; // should be empty in case when discipline mark wasn't found.
    }

    private boolean isAppropriateDisciplineName(final String discipline, final String total) {
        final int levenshteinDistance = StringUtils.getLevenshteinDistance(discipline, total);
        final double antiSimilarityCoefficient = levenshteinDistance * 1.0 / Math.max(discipline.length(), total.length());
        return antiSimilarityCoefficient < MIN_SIMILARITY_COEFFICIENT && total.startsWith(discipline.substring(0, PREFIX_LENGTH))
                || total.startsWith(discipline.substring(0, discipline.length() / 3));
    }

    @Override
    public List<StudentProfile> find() {
        return studentDao.findAll();
    }

    @Override
    public void setCredentials(final StudentProfile studentProfile) {
        final String studentEmail = getStudentEmail(studentProfile);
        if (StringUtils.isNotBlank(studentEmail)) {
            final User user = new User();
            user.setId(studentEmail);
            studentProfile.setEmail(studentEmail);
            //user.setPassword("0000");
            final String password = UUID.randomUUID().toString().substring(0, 8);
            user.setPassword(password);
            studentProfile.setPassword(password);
            user.setRole(2);
            userService.save(user);
        } else {
            throw new RuntimeException("Cannot found email for the user!");
        }
    }

    @Override
    public void setGroupId(final StudentProfile studentProfile) {
        final Group group = groupDao.findOne(studentProfile.getGroup());
        studentProfile.setGroupId(group.getId());
    }

    @Override
    public void sendEmailAfterProfileCreation(final StudentProfile studentProfile) {
        try {
            //// TODO: 27.06.16 Remove comments
            //setTo(studentProfile.getId());
            final Map<String, Object> modelForVelocity = ImmutableMap.of("password", studentProfile.getPassword(), "name", studentProfile.getName());
            final MimeMessage mimeMessage = emailService.new MimeMessageBuilder("oleksandr.rozdolskyi@epam.com", supportMail)
                    .setSubject("Кабінет студента | Пароль для входу")
                    .setText(emailService.createHtmlFromVelocityTemplate(SEND_PASSWORD_VM_TEMPLATE, modelForVelocity), true)
                    .build();
            emailService.send(mimeMessage);
        } catch (final RuntimeException e) {
            LOG.warn(e);
        }
    }

    @Override
    public void sendEmailAfterProfileUpdating(final StudentProfile studentProfile) {
        try {
            //// TODO: 27.06.16 Remove comments
            //setTo(studentProfile.getId());
            final Map<String, Object> modelForVelocity = ImmutableMap.of("message", "Ваш профіль був оновлений. Перейдійть у кабінет для перегляду.");
            final MimeMessage mimeMessage = emailService.new MimeMessageBuilder("oleksandr.rozdolskyi@epam.com", supportMail)
                    .setSubject("Кабінет студента | Зміни в профілі")
                    .setText(emailService.createHtmlFromVelocityTemplate(SEND_PROFILE_WAS_CHANGED_VM_TEMPLATE, modelForVelocity), true)
                    .build();
            emailService.send(mimeMessage);
        } catch (final RuntimeException e) {
            LOG.warn(e);
        }
    }

    @Override
    public List<StudentProfile> find(final String searchCriteria, final Integer page) {
        return studentDao.find(searchCriteria, page);
    }

    @Override
    public long getPagesCountForSearchCriteria(final String searchCriteria) {
        return studentDao.getPagesCountForSearchCriteria(searchCriteria);
    }

    @Override
    public void remove(final String id) {
        studentDao.remove(id);
    }

    private String getStudentEmail(final StudentProfile studentProfile) {
        try {
            final String name = studentProfile.getName().toLowerCase().split(" ")[0];
            final String surname = studentProfile.getSurname().toLowerCase();
            final RestTemplate restTemplate = new RestTemplate();
            return restTemplate
                    .getForEntity(String.format(emailsIntegrationServiceUrl + GET_STUDENT_EMAIL_URL, name, surname, studentProfile.getGroup()), String.class)
                    .getBody();
        } catch (final RuntimeException e) {
            LOG.warn("Cannot receive the email!", e);
            return StringUtils.EMPTY;
        }

    }
}
