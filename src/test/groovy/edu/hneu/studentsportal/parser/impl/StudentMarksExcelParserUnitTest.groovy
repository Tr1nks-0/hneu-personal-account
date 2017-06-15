package edu.hneu.studentsportal.parser.impl

import edu.hneu.studentsportal.domain.*
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.repository.GroupRepository
import edu.hneu.studentsportal.repository.StudentRepository
import edu.hneu.studentsportal.service.DisciplineMarkService
import edu.hneu.studentsportal.service.DisciplineService
import spock.lang.Shared
import spock.lang.Specification

class StudentMarksExcelParserUnitTest extends Specification {

    static final String STUDENTS_MARKS = 'parser/8.04.51.16.04.xlsx'

    @Shared def groupMock = Mock(Group)
    @Shared def specialityMock = Mock(Speciality)
    @Shared def educationProgramMock = Mock(EducationProgram)
    @Shared def disciplineMock = Mock(Discipline)

    def groupRepositoryMock = Mock(GroupRepository)
    def disciplineRepositoryMock = Mock(DisciplineRepository)
    def studentRepositoryMock = Mock(StudentRepository)
    def disciplineMarkServiceMock = Mock(DisciplineMarkService)
    def disciplineServiceMock = Mock(DisciplineService)

    def studentMarksExcelParser = new StudentMarksExcelParser(
            groupRepository: groupRepositoryMock,
            disciplineRepository: disciplineRepositoryMock,
            studentRepository: studentRepositoryMock,
            disciplineMarkService: disciplineMarkServiceMock,
            disciplineService: disciplineServiceMock
    )

    def setup() {
        groupRepositoryMock.findByName('8.04.51.16.04') >> Optional.of(groupMock)
        groupMock.getSpeciality() >> specialityMock
        groupMock.getEducationProgram() >> educationProgramMock
        disciplineRepositoryMock.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(*_) >> Optional.of(disciplineMock)
        studentRepositoryMock.findByGroup(groupMock) >> [
            new Student(name: 'Олександр', surname: 'Аніщік'),
            new Student(name: 'Богдан', surname: 'Безчасний'),
            new Student(name: 'Іван', surname: 'Білодід'),
            new Student(name: 'Сергій', surname: 'Головаш'),
            new Student(name: 'Сергій', surname: 'Гордієнко'),
            new Student(name: 'Володимир', surname: 'Ковтун'),
            new Student(name: 'Антон', surname: 'Комишан'),
            new Student(name: 'Валерій', surname: 'Константинов'),
            new Student(name: 'Руслан', surname: 'Курило'),
            new Student(name: 'Єгор', surname: 'Лєкарєв'),
            new Student(name: 'Костянтин', surname: 'Молчанов'),
            new Student(name: 'Владислав', surname: 'Нейчев'),
            new Student(name: 'Олександр', surname: 'Роздольський'),
            new Student(name: 'Максим', surname: 'Сизранцев'),
            new Student(name: 'Валентин', surname: 'Чугреєв'),
        ]
        disciplineMarkServiceMock.alignStudentDisciplinesMark(*_) >> [
                disciplineMock, disciplineMock, disciplineMock,
                disciplineMock, disciplineMock, disciplineMock,
        ]
    }

    def 'should have marks for all student from the group'() {
        given:
            def excelFile = loadResource STUDENTS_MARKS
        when:
            def marksPerStudent = studentMarksExcelParser.parse(excelFile)
        then:
            marksPerStudent.size() == 15
    }

    def 'should have all disciplines from file'() {
        given:
            def excelFile = loadResource STUDENTS_MARKS
        when:
            def marksPerStudent = studentMarksExcelParser.parse(excelFile)
        then:
            marksPerStudent.values()[0].size() == 6
    }

    def loadResource = { resourceName ->
        new File(this.class.classLoader.getResource(resourceName).getFile())
    }
}
