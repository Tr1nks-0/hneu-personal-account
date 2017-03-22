package edu.hneu.studentsportal.parser

import edu.hneu.studentsportal.entity.*
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
    @Shared def disciplineMock = Mock(Discipline)

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
        facultyRepositoryMock.findByName(_) >> Optional.of(facultyMock)
        specialityRepositoryMock.findByNameAndFaculty(_, facultyMock) >> Optional.of(specialityMock)
        educationProgramRepositoryMock.findByName(_) >> Optional.of(educationProgramMock)
        disciplineRepositoryMock._(*_) >> Optional.of(disciplineMock)
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
    def 'student from #excelFileName should have [#numberOfDisciplines] disciplines'() {
        given:
            def excelFile = loadResource excelFileName
        when:
            def profile = studentProfileExcelParser.parse(excelFile)
        then:
            profile.disciplineMarks.size() == numberOfDisciplines
        where:
            excelFileName            | numberOfDisciplines
            MASTER_STUDENT_PROFILE   | 13
            BACHELOR_STUDENT_PROFILE | 43

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
