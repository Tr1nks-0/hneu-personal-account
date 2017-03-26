package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.Discipline;
import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.repository.StudentDao;
import edu.hneu.studentsportal.repository.StudentRepository;
import edu.hneu.studentsportal.service.StudentService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.*;

@Log4j
@Service
public class DefaultStudentService implements StudentService {

    private static final int PREFIX_LENGTH = 5;
    private static final double MIN_SIMILARITY_COEFFICIENT = 0.6;
    private static final Map<String, String> semesters = new HashMap<>();
    private static final String SEND_PASSWORD_VM_TEMPLATE = "velocity/sendProfileWasCreated.vm";
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
    private WebApplicationContext context;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DefaultEmailService emailService;
    @Autowired
    private GmailService gmailService;
    @Autowired
    private StudentRepository studentRepository;

    @Value("${support.mail}")
    public String supportMail;


    @Override
    public void save(Student studentProfile) {
        studentProfile.setModificationTime(new Date().getTime());
        studentRepository.save(studentProfile);
    }

    @Override
    public Student getStudent(long id) {
        return studentRepository.findOne(id);
    }


    @Override
    public Optional<Student> findStudentProfileByEmail(final String email) {
        return studentRepository.findByEmail(email);
    }

    public void updateStudentsScoresFromFile(final File file) {
      /*  final PointsWrapper studentsPointsWrapper = new StudentMarksExcelParser().parse(file);
        for (final Map.Entry<String, Map<String, String>> studentScore : studentsPointsWrapper.getMap().entrySet()) {
            final Student studentProfile = getStudentProfile(studentScore);
            if (nonNull(studentProfile)) {
                //final String semesterId = studentsPointsWrapper.getSemester();
                //updateStudentProfileSemester(studentProfile, semesterId, studentScore.getValue());
                save(studentProfile);
                sendEmailAfterProfileUpdating(studentProfile);
            }
        }*/
    }

    private Student getStudentProfile(final Map.Entry<String, Map<String, String>> studentScore) {
        final String[] keys = studentScore.getKey().split("\\$");
        if (keys.length == 2) {
            final String subKey = keys[0];
            final String groupCode = keys[1];
            return findStudentProfile(subKey, groupCode);
        }
        return null;
    }

    private Student findStudentProfile(final String subKey, final String groupCode) {
        return studentDao.find(subKey, groupCode);
    }

/*
    private void updateStudentProfileSemester(final Student studentProfile, final String semesterId, final Map<String, String> studentScore) {
        final Optional<Semester> semester = findSemesterForLabel(studentProfile, semesterId);
        if (semester.isPresent()) {
            final List<Discipline> disciplines = semester.get().getDisciplines();
            for (final Discipline discipline : disciplines)
                discipline.setMark(getDisciplineScore(studentScore, discipline.getLabel()));
            if (!studentScore.isEmpty() && studentScore.values().stream().noneMatch("-"::equals))
                throw new RuntimeException("Some disciplines were not found");
        }
    }
*/

    private Discipline getEmptyDiscipline(final List<Discipline> disciplines) {
        return disciplines.stream().filter(discipline -> StringUtils.isEmpty(discipline.getLabel())).findFirst().orElse(new Discipline());
    }
/*

    private Optional<Semester> findSemesterForLabel(final Student studentProfile, final String semesterId) {
        for (final Course course : studentProfile.getCourses())
            for (final Semester semester : course.getSemesters())
                if (Objects.equals(semesters.get(semesterId), semester.getLabel()))
                    return Optional.of(semester);
        return Optional.empty();
    }
*/

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
        return antiSimilarityCoefficient < MIN_SIMILARITY_COEFFICIENT && total.startsWith(discipline.substring(0, PREFIX_LENGTH)) ||
                total.startsWith(discipline.substring(0, discipline.length() / 3));
    }

    @Override
    public List<Student> find() {
        return studentRepository.findAll();
    }

    @Override
    public void setGroupId(final Student studentProfile) {
        // final Group group = groupDao.findOne(studentProfile.getStudentGroup());
        // studentProfile.setStudentGroupId(group.getId());
    }

    @Override
    public void sendEmailAfterProfileCreation(final Student studentProfile) {
        /*try {
            final Map<String, Object> modelForVelocity = ImmutableMap.of("name", studentProfile.getName());
            //@formatter:off
            final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(
                    supportMail, studentProfile.getEmail())
                    .setSubject("Кабінет студента | Вхід")
                    .setText(emailService.createHtmlFromVelocityTemplate(SEND_PASSWORD_VM_TEMPLATE, modelForVelocity), true)
                    .build();
            //@formatter:on
            gmailService.api().users().messages().send(supportMail, gmailService.convertToGmailMessage(mimeMessage)).execute();
        } catch (final Exception e) {
            LOG.warn(e);
        }*/
    }

    @Override
    public void sendEmailAfterProfileUpdating(final Student studentProfile) {
        /*
         * try { final Map<String, Object> modelForVelocity = ImmutableMap.of("message", "Ваш профіль був оновлений. Перейдійть у кабінет для перегляду.");
         * final MimeMessage mimeMessage = emailService.new MimeMessageBuilder(supportMail, studentProfile.getEmail())
         * .setSubject("Кабінет студента | Зміни в профілі") .setText(emailService.createHtmlFromVelocityTemplate(SEND_PROFILE_WAS_CHANGED_VM_TEMPLATE,
         * modelForVelocity), true).build(); gmailService.api().users().messages().send(supportMail, gmailService.convertToGmailMessage(mimeMessage)).execute();
         * } catch (final Exception e) { LOG.warn(e); }
         */
    }

    @Override
    public List<Student> find(final String searchCriteria, final Integer page) {
        return studentDao.find(searchCriteria, page);
    }

    @Override
    public long getPagesCountForSearchCriteria(final String searchCriteria) {
        return studentDao.getPagesCountForSearchCriteria(searchCriteria);
    }

    @Override
    public void remove(final long id) {
        studentRepository.delete(id);
    }

    @Override
    public void refreshMarks() {
        /*final Map<String, List<Student>> studentsPerSpecialityAndCourse = studentRepository.findAll().stream()
                .collect(Collectors.groupingBy(s -> s.getIncomeYear() + "$" + s.getSpeciality(), Collectors.mapping(s -> s, Collectors.toList())));
        studentsPerSpecialityAndCourse.values().forEach(students -> {
            students.forEach(student -> {
                final List<Discipline> allStudentDisciplines = extractDisciplinesFunction.apply(student);
                final Double studentAverage = calculateAverageFunction.apply(allStudentDisciplines);
                if (isFalse(studentAverage.isNaN())) {
                    student.setAverage(new BigDecimal(studentAverage).setScale(2, RoundingMode.HALF_UP).doubleValue());
                } else {
                    student.setAverage(0.0);
                }
            });
            Collections.sort(students, (s1, s2) -> NumberUtils.compare(s2.getAverage(), s1.getAverage()));
            IntStream.range(0, students.size()).forEach(i -> students.get(i).setRate(i + 1));
            students.forEach(studentRepository::save);
        });*/
    }
/*

    Function<List<Discipline>, Double> calculateAverageFunction = disciplines -> {
        double total = disciplines.stream().map(Discipline::getMark).mapToDouble(Double::valueOf).sum();
        return total / disciplines.size();
    };
*/

/*    Function<Student, List<Discipline>> extractDisciplinesFunction = student -> student.getCourses().stream().flatMap(c -> c.getSemesters().stream())
            .flatMap(s -> s.getDisciplines().stream()).filter(d -> NumberUtils.isNumber(d.getMark())).collect(Collectors.toList());*/
}
