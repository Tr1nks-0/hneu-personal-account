package edu.hneu.studentsportal.service

import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.domain.DisciplineMark
import edu.hneu.studentsportal.domain.Student
import edu.hneu.studentsportal.parser.factory.ParserFactory
import edu.hneu.studentsportal.parser.impl.DisciplinesExcelParser
import edu.hneu.studentsportal.parser.impl.StudentMarksExcelParser
import edu.hneu.studentsportal.parser.impl.StudentsChoiceExcelParser
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.repository.StudentRepository
import spock.lang.Specification

class ImportServiceUnitSpockTest extends Specification {

    def fileMock = Mock(File)
    def parseFactoryMock = Mock(ParserFactory)
    def disciplinesExcelParserMock = Mock(DisciplinesExcelParser)
    def studentsChoiceExcelParserMock = Mock(StudentsChoiceExcelParser)
    def studentMarksExcelParserMock = Mock(StudentMarksExcelParser)
    def disciplineRepositoryMock = Mock(DisciplineRepository)
    def studentRepositoryMock = Mock(StudentRepository)
    def disciplineMarkServiceMock = Mock(DisciplineMarkService)
    def disciplineMock1 = Mock(Discipline)
    def disciplineMock2 = Mock(Discipline)
    def disciplineMarkMock1 = Mock(DisciplineMark)
    def disciplineMarkMock2 = Mock(DisciplineMark)
    def studentMock = Mock(Student)

    def disciplines = [disciplineMock1, disciplineMock2]

    def importService = new ImportService(
            parseFactoryMock,
            studentRepositoryMock,
            disciplineMarkServiceMock,
            disciplineRepositoryMock
    )

    def setup() {
        parseFactoryMock.newDisciplinesParser() >> disciplinesExcelParserMock
        parseFactoryMock.newStudentsChoiceParser() >> studentsChoiceExcelParserMock
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

    def 'should update imported marks with existed for a student when import student marks'() {
        given:
        parseFactoryMock.newStudentMarksExcelParser() >> studentMarksExcelParserMock
        def importedMarks = [disciplineMarkMock1, disciplineMarkMock2]
        studentMarksExcelParserMock.parse(fileMock) >> [(studentMock): importedMarks]
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

