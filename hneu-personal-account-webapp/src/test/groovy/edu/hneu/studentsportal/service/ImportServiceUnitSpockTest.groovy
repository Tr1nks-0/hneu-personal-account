package edu.hneu.studentsportal.service

import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.domain.DisciplineMark
import edu.hneu.studentsportal.domain.Student
import edu.hneu.studentsportal.enums.UserRole
import edu.hneu.studentsportal.parser.factory.ParserFactory
import edu.hneu.studentsportal.parser.impl.DisciplinesExcelParser
import edu.hneu.studentsportal.parser.impl.StudentMarksExcelParser
import edu.hneu.studentsportal.parser.impl.StudentProfileExcelParser
import edu.hneu.studentsportal.parser.impl.StudentsChoiceExcelParser
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.repository.StudentRepository
import spock.lang.Specification

class ImportServiceUnitSpockTest extends Specification {

    def fileMock = Mock(File)
    def parseFactoryMock = Mock(ParserFactory)
    def disciplinesExcelParserMock = Mock(DisciplinesExcelParser)
    def studentsChoiceExcelParserMock = Mock(StudentsChoiceExcelParser)
    def studentProfileExcelParserMock = Mock(StudentProfileExcelParser)
    def studentMarksExcelParserMock = Mock(StudentMarksExcelParser)
    def disciplineRepositoryMock = Mock(DisciplineRepository)
    def studentRepositoryMock = Mock(StudentRepository)
    def disciplineMarkServiceMock = Mock(DisciplineMarkService)
    def studentEmailReceivingServiceMock = Mock(StudentEmailReceivingService)
    def userServiceMock = Mock(UserService)
    def disciplineMock1 = Mock(Discipline)
    def disciplineMock2 = Mock(Discipline)
    def disciplineMarkMock1 = Mock(DisciplineMark)
    def disciplineMarkMock2 = Mock(DisciplineMark)
    def studentMock = Mock(Student)

    def disciplines = [disciplineMock1, disciplineMock2]
    def email = 'test@test.ua'

    def importService = new ImportService(
            parseFactoryMock,
            userServiceMock,
            studentRepositoryMock,
            disciplineMarkServiceMock,
            disciplineRepositoryMock,
            studentEmailReceivingServiceMock
    )

    def setup() {
        parseFactoryMock.newDisciplinesParser() >> disciplinesExcelParserMock
        parseFactoryMock.newStudentsChoiceParser() >> studentsChoiceExcelParserMock
        parseFactoryMock.newStudentProfileExcelParser() >> studentProfileExcelParserMock
        disciplineMarkMock1.discipline >> disciplineMock1
        disciplineMarkMock2.discipline >> disciplineMock2
    }

    def 'should parse disciplines when import disciplines'() {
        given:
            disciplinesExcelParserMock.parse(fileMock) >> disciplines
        when:
            def actual = importService.importDisciplines(fileMock)
        then:
            disciplines == actual
    }

    def 'should save all parsed disciplines when import disciplines'() {
        given:
            disciplinesExcelParserMock.parse(fileMock) >> disciplines
        when:
            importService.importDisciplines(fileMock)
        then:
            1 * disciplineRepositoryMock.save(disciplines)
    }

    def 'should parse students choice and add only new disciplines'() {
        given:
            studentsChoiceExcelParserMock.parse(fileMock) >> [(studentMock): [disciplineMock1, disciplineMock2]]
            studentMock.disciplineMarks >> [disciplineMarkMock1]
            disciplineMarkServiceMock.extract(*_) >> [disciplineMock1]
        when:
            def studentChoice = importService.importStudentsChoice(fileMock)
        then:
            1 == studentChoice.size()
            studentChoice.keySet().contains(studentMock)
            [disciplineMock1, disciplineMock2] == studentChoice.get(studentMock)

    }

    def 'should create a student when import student'() {
        given:
            studentProfileExcelParserMock.parse(fileMock) >> studentMock
        when:
            def student = importService.importStudent(fileMock)
        then:
            studentMock == student
            1 * studentRepositoryMock.save(studentMock)
    }

//    def 'should update created student with email when import student'() {
//        given:
//            studentProfileExcelParserMock.parse(fileMock) >> studentMock
//            studentEmailReceivingServiceMock.receiveStudentEmail(studentMock) >> email
//        when:
//            importService.importStudent(fileMock)
//        then:
//            1 * studentMock.setEmail(email)
//    }

    def 'should create user in the system when import student'() {
        given:
            studentProfileExcelParserMock.parse(fileMock) >> studentMock
            studentMock.email >> email
        when:
            importService.importStudent(fileMock)
        then:
            1 * userServiceMock.create(email, UserRole.STUDENT)
    }

    def 'should update imported marks with existed for a student when import student marks'() {
        given:
            parseFactoryMock.newStudentMarksExcelParser() >> studentMarksExcelParserMock
            def importedMarks = [disciplineMarkMock1, disciplineMarkMock2]
            studentMarksExcelParserMock.parse(fileMock) >> [(studentMock) : importedMarks]
            disciplineMarkServiceMock.updateStudentMarks(studentMock, importedMarks) >> importedMarks
        when:
            def studentMarks = importService.importStudentMarks(fileMock)
        then:
            1 == studentMarks.size()
            studentMarks.keySet().contains(studentMock)
            importedMarks == studentMarks.get(studentMock)
            1 * studentRepositoryMock.save(studentMock)
    }
}

