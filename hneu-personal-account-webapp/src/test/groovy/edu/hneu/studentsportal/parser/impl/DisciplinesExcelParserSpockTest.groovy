package edu.hneu.studentsportal.parser.impl

import edu.hneu.studentsportal.domain.EducationProgram
import edu.hneu.studentsportal.domain.Speciality
import edu.hneu.studentsportal.repository.EducationProgramRepository
import edu.hneu.studentsportal.repository.SpecialityRepository
import spock.lang.Specification

class DisciplinesExcelParserSpockTest extends Specification {

    static final String DISCIPLINES_FILE = 'parser/disciplines.xlsx'

    def specialityRepositoryMock = Mock(SpecialityRepository)
    def educationProgramRepositoryMock = Mock(EducationProgramRepository)

    def specialityMock = Mock(Speciality)
    def educationProgramMock = Mock(EducationProgram)

    def disciplinesExcelParser = new DisciplinesExcelParser(
            specialityRepository: specialityRepositoryMock,
            educationProgramRepository: educationProgramRepositoryMock
    )

    def setup() {
        specialityRepositoryMock.findById(122) >> Optional.of(specialityMock)
        educationProgramRepositoryMock.findById(122) >> educationProgramMock
    }

    def 'should parse only disciplines with existed faculties, specialities, education programs'() {
        given:
            def excelFile = loadResource DISCIPLINES_FILE
        when:
            def disciplines = disciplinesExcelParser.parse(excelFile)
        then:
            disciplines.size() == 18
    }

    def loadResource = {
        resourceName -> new File(this.class.classLoader.getResource(resourceName).getFile())
    }
}

