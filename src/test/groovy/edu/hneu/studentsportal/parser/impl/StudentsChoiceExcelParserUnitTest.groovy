package edu.hneu.studentsportal.parser.impl

import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.domain.Group
import edu.hneu.studentsportal.domain.Student
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.repository.GroupRepository
import edu.hneu.studentsportal.repository.StudentRepository
import spock.lang.Shared
import spock.lang.Specification

class StudentsChoiceExcelParserUnitTest extends Specification {

    static final String STUDENTS_CHOICE_FILE = 'parser/students_choice.xlsx'
    static final String GROUP1 = '8.02.22.16.04'
    static final String GROUP2 = '8.04.51.16.04'

    @Shared
    def groupMock = Mock(Group)
    @Shared
    def studentMock = Mock(Student)
    @Shared
    def discipline = Mock(Discipline)

    def studentRepositoryMock = Mock(StudentRepository)
    def groupRepositoryMock = Mock(GroupRepository)
    def disciplineRepositoryMock = Mock(DisciplineRepository)

    def studentChoiceExcelParser = new StudentsChoiceExcelParser(
            studentRepository: studentRepositoryMock,
            groupRepository: groupRepositoryMock,
            disciplineRepository: disciplineRepositoryMock
    )

    def setup() {
        groupRepositoryMock.findByName(GROUP1) >> Optional.empty()
        groupRepositoryMock.findByName(GROUP2) >> Optional.of(groupMock)
        studentRepositoryMock.findByNameAndSurnameAndGroup('Олександр Юрійович', 'Роздольський', groupMock) >>
                Optional.of(studentMock)

        disciplineRepositoryMock.findByCodeAndCourseAndSemesterAndSpecialityAndEducationProgram(*_) >> Optional.of(discipline)
    }

    def 'should download only users from existed group and that are present in the storage'() {
        given:
            def excelFile = loadResource STUDENTS_CHOICE_FILE
        when:
            def studentsChoice = studentChoiceExcelParser.parse(excelFile)
        then:
            studentsChoice.keySet().contains(studentMock)
    }


    def 'should download only users that are present in the storage'() {
        given:
            def excelFile = loadResource STUDENTS_CHOICE_FILE
            groupRepositoryMock.findByName(GROUP1) >> Optional.of(groupMock)
        when:
            def studentsChoice = studentChoiceExcelParser.parse(excelFile)
        then:
            studentsChoice.size() == 1
    }

    def 'should add existed disciplines to the student choice list'() {
        given:
            def excelFile = loadResource STUDENTS_CHOICE_FILE
        when:
            def studentsChoice = studentChoiceExcelParser.parse(excelFile)
        then:
            studentsChoice.get(studentMock).size() == 2
    }

    def loadResource = {
        resourceName -> new File(this.class.classLoader.getResource(resourceName).getFile())
    }
}

