package edu.hneu.studentsportal.parser.impl

import edu.hneu.studentsportal.domain.*
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.repository.GroupRepository
import edu.hneu.studentsportal.repository.StudentRepository
import edu.hneu.studentsportal.service.DisciplineMarkService
import edu.hneu.studentsportal.conditions.DisciplineConditions
import spock.lang.Specification

class StudentMarksExcelParserSpockTest extends Specification {

    static final String STUDENTS_MARKS = 'parser/8.04.51.16.04.xlsx'

    def groupMock = Mock(Group)
    def specialityMock = Mock(Speciality)
    def educationProgramMock = Mock(EducationProgram)
    def disciplineMock = Mock(Discipline)
    def groupRepositoryMock = Mock(GroupRepository)
    def disciplineRepositoryMock = Mock(DisciplineRepository)
    def studentRepositoryMock = Mock(StudentRepository)
    def disciplineMarkServiceMock = Mock(DisciplineMarkService)
    def disciplineServiceMock = Mock(DisciplineConditions)

    def studentMarksExcelParser = new StudentMarksExcelParser(
            groupRepository: groupRepositoryMock,
            disciplineRepository: disciplineRepositoryMock,
            studentRepository: studentRepositoryMock,
            disciplineMarkService: disciplineMarkServiceMock,
            disciplineConditions: disciplineServiceMock
    )

    def setup() {
        groupRepositoryMock.findByName('8.04.51.16.04') >> Optional.of(groupMock)
        groupMock.getSpeciality() >> specialityMock
        groupMock.getEducationProgram() >> educationProgramMock
        disciplineRepositoryMock.findByLabelAndCourseAndSemesterAndSpecialityAndEducationProgram(*_) >> Optional.of(disciplineMock)
        studentRepositoryMock.findByGroup(groupMock) >> [
            new Student(id: 1, name: 'Олександр', surname: 'Аніщік'),
            new Student(id: 2, name: 'Богдан', surname: 'Безчасний'),
            new Student(id: 3, name: 'Іван', surname: 'Білодід'),
            new Student(id: 4, name: 'Сергій', surname: 'Головаш'),
            new Student(id: 5, name: 'Сергій', surname: 'Гордієнко'),
            new Student(id: 6, name: 'Володимир', surname: 'Ковтун'),
            new Student(id: 7, name: 'Антон', surname: 'Комишан'),
            new Student(id: 8, name: 'Валерій', surname: 'Константинов'),
            new Student(id: 9, name: 'Руслан', surname: 'Курило'),
            new Student(id: 10, name: 'Єгор', surname: 'Лєкарєв'),
            new Student(id: 11, name: 'Костянтин', surname: 'Молчанов'),
            new Student(id: 12, name: 'Владислав', surname: 'Нейчев'),
            new Student(id: 13, name: 'Олександр', surname: 'Роздольський'),
            new Student(id: 14, name: 'Максим', surname: 'Сизранцев'),
            new Student(id: 15, name: 'Валентин', surname: 'Чугреєв'),
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
