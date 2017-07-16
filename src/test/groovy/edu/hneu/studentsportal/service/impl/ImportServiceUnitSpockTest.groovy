package edu.hneu.studentsportal.service.impl

import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.parser.factory.ParserFactory
import edu.hneu.studentsportal.parser.impl.DisciplinesExcelParser
import edu.hneu.studentsportal.repository.DisciplineRepository
import edu.hneu.studentsportal.service.ImportService
import spock.lang.Specification

class ImportServiceUnitSpockTest extends Specification {

    def file = Mock(File)
    def parseFactoryMock = Mock(ParserFactory)
    def disciplinesExcelParserMock = Mock(DisciplinesExcelParser)
    def disciplineMock1 = Mock(Discipline)
    def disciplineMock2 = Mock(Discipline)
    def disciplineRepositoryMock = Mock(DisciplineRepository)

    def disciplines = [disciplineMock1, disciplineMock2]

    def importService = new ImportService(
            parserFactory: parseFactoryMock,
            disciplineRepository: disciplineRepositoryMock
    )

    def setup() {
        parseFactoryMock.newDisciplinesParser() >> disciplinesExcelParserMock
        disciplinesExcelParserMock.parse(file) >> disciplines
    }

    def 'should parse disciplines'() {
        when:
            def actual = importService.importDisciplines(file)
        then:
            disciplines == actual
    }

    def 'should save all parsed disciplines'() {
        when:
            importService.importDisciplines(file)
        then:
            1 * disciplineRepositoryMock.save(disciplines)
    }
}

