package edu.hneu.studentsportal.parser

import edu.hneu.studentsportal.entity.EducationProgram
import edu.hneu.studentsportal.entity.Faculty
import edu.hneu.studentsportal.entity.Group
import edu.hneu.studentsportal.entity.Speciality
import edu.hneu.studentsportal.parser.impl.StudentProfileExcelParser
import edu.hneu.studentsportal.repository.*
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class StudentProfileExcelParserUnitTest extends Specification {

    static final String MASTER_STUDENT_PROFILE = 'parser/master.xlsx'
    static final String BACHELOR_STUDENT_PROFILE = 'parser/bachelor.xlsx'

    @Shared def bachelorGroupMock = Mock(Group)
    @Shared def masterGroupMock = Mock(Group)
    @Shared def facultyMock = Mock(Faculty)
    @Shared def specialityMock = Mock(Speciality)
    @Shared def educationProgramMock = Mock(EducationProgram)

    def groupRepositoryMock = Mock(GroupRepository)
    def disciplineRepositoryMock = Mock(DisciplineRepository)
    def facultyRepositoryMock = Mock(FacultyRepository)
    def specialityRepositoryMock = Mock(SpecialityRepository)
    def educationProgramRepositoryMock = Mock(EducationProgramRepository)
    def studentProfileExcelParser = new StudentProfileExcelParser(
            groupRepository: groupRepositoryMock,
            disciplineRepository: disciplineRepositoryMock,
            facultyRepository: facultyRepositoryMock,
            specialityRepository: specialityRepositoryMock,
            educationProgramRepository: educationProgramRepositoryMock,
    )

    def setup() {
        groupRepositoryMock.findByName('6.04.51.16.03') >> Optional.of(bachelorGroupMock)
        groupRepositoryMock.findByName('8.04.51.16.04') >> Optional.of(masterGroupMock)
        facultyRepositoryMock.findByName('Економічної інформатики') >> Optional.of(facultyMock)
        specialityRepositoryMock.findByNameAndFaculty('122 КОМП\'ЮТЕРНІ НАУКИ ТА ІНФОРМАЦІЙНІ ТЕХНОЛОГІЇ', facultyMock) >> Optional.of(specialityMock)
        educationProgramRepositoryMock.findByName('ІНФОРМАЦІЙНІ СИСТЕМИ УПРАВЛІННЯ ТА ТЕХНОЛОГІЇ ОБРОБКИ ДАНИХ') >> Optional.of(educationProgramMock)
        disciplineRepositoryMock.findOne(any()) >> Optional.empty()
    }

    @Unroll
    def 'student from #excelFileName should have full name[#studentFullName]'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.name == studentFullName
        where:
            excelFileName            | studentFullName
            MASTER_STUDENT_PROFILE   | 'Олександр Юрійович'
            BACHELOR_STUDENT_PROFILE | 'Олександр Андрійович'
    }

    @Unroll
    def 'student from #excelFileName should have surname[#surname]'() {
        given:
           def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.surname == surname
        where:
            excelFileName            | surname
            MASTER_STUDENT_PROFILE   | 'Роздольський'
            BACHELOR_STUDENT_PROFILE | 'Скороход'
    }

    @Unroll
    def 'student from #excelFileName should have faculty'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.faculty == facultyMock
        where:
            excelFileName            | _
            MASTER_STUDENT_PROFILE   | _
            BACHELOR_STUDENT_PROFILE | _
    }

    @Unroll
    def 'student from #excelFileName should have income year[#expectedIncomeYear]'() {
        given:
           def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.incomeYear == expectedIncomeYear
        where:
            expectedIncomeYear = 2016
            excelFileName            | _
            MASTER_STUDENT_PROFILE   | _
            BACHELOR_STUDENT_PROFILE | _
    }

    @Unroll
    def 'student from #excelFileName should have speciality'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.speciality == specialityMock
        where:
            excelFileName            | _
            MASTER_STUDENT_PROFILE   | _
            BACHELOR_STUDENT_PROFILE | _
    }

    @Unroll
    def 'student from #excelFileName should have contacts[#contactInfo]'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.contactInfo == contactInfo
        where:
            excelFileName            | contactInfo
            MASTER_STUDENT_PROFILE   | ['0506584098', 'oleksandr.rozdolskyi2012@gmail.com']
            BACHELOR_STUDENT_PROFILE | ['0952168010', 'mopis101@gmail.com']
    }

    @Unroll
    def 'student from #excelFileName should have group[#studentGroup]'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.studentGroup == studentGroup
        where:
            excelFileName            | studentGroup
            MASTER_STUDENT_PROFILE   | masterGroupMock
            BACHELOR_STUDENT_PROFILE | bachelorGroupMock
    }

    @Unroll
    def 'student from #excelFileName should have [#numberOfDisciplines] disciplines on the [#course] course in the [#semester] semester'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:

            profile.disciplineMarks.stream().filter{ discipline -> discipline.course == course && discipline.semester == semester}.count() == numberOfDisciplines
        where:
            excelFileName            | course | semester | numberOfDisciplines
            MASTER_STUDENT_PROFILE   | 1      | 1        | 4
            MASTER_STUDENT_PROFILE   | 1      | 2        | 5
            MASTER_STUDENT_PROFILE   | 2      | 1        | 4
            BACHELOR_STUDENT_PROFILE | 1      | 1        | 7
            BACHELOR_STUDENT_PROFILE | 1      | 2        | 7
            BACHELOR_STUDENT_PROFILE | 2      | 1        | 5
            BACHELOR_STUDENT_PROFILE | 2      | 2        | 6
            BACHELOR_STUDENT_PROFILE | 3      | 1        | 6
            BACHELOR_STUDENT_PROFILE | 3      | 2        | 3
            BACHELOR_STUDENT_PROFILE | 4      | 1        | 4
            BACHELOR_STUDENT_PROFILE | 4      | 2        | 5
    }

    @Unroll
    def 'student from #excelFileName should have photo'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.getPhoto() != null
        where:
            excelFileName            | _
            MASTER_STUDENT_PROFILE   | _
            BACHELOR_STUDENT_PROFILE | _
    }

    def loadResource = { resourceName ->
        new File(this.class.classLoader.getResource(resourceName).getFile())
    }
}
