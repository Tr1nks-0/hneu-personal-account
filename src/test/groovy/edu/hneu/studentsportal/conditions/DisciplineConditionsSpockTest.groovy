package edu.hneu.studentsportal.conditions

import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.enums.DisciplineType
import spock.lang.Specification
import spock.lang.Unroll

class DisciplineConditionsSpockTest extends Specification {

    @Unroll
    def 'should return #expected when DisciplineConditions.isMasterDiscipline called for #disciplineType'() {
        given:
            def discipline = Mock(Discipline)
            discipline.getType() >> disciplineType
        when:
            def actual = DisciplineConditions.isMasterDiscipline(discipline)
        then:
            expected == actual
        where:
            disciplineType           | expected
            DisciplineType.MAJOR     | false
            DisciplineType.MAYNOR    | false
            DisciplineType.REGULAR   | false
            DisciplineType.MAGMAYNOR | true
    }

    def 'should throw an exception when discipline is null and DisciplineConditions.isMasterDiscipline called'() {
        when:
            DisciplineConditions.isMasterDiscipline(null)
        then:
            thrown IllegalArgumentException
    }

    @Unroll
    def 'should return #expected when DisciplineConditions.isMasterDisciplineLabel called for #label'() {
        when:
            def actual = DisciplineConditions.isMasterDisciplineLabel(label)
        then:
            expected == actual
        where:
            label             | expected
            'маголего'        | true
            'Маголего'        | true
            'Вища математика' | false
    }

    @Unroll
    def 'should throw an exception when disciplines label is #type and DisciplineConditions.isMasterDisciplineLabel called'() {
        when:
            DisciplineConditions.isMasterDisciplineLabel(label)
        then:
            thrown IllegalArgumentException
        where:
            label | type
            ''    | 'empty'
            null  | 'null'
    }
}
