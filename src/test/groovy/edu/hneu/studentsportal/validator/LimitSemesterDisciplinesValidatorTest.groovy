package edu.hneu.studentsportal.validator

import edu.hneu.studentsportal.annotation.LimitSemesterDisciplines
import edu.hneu.studentsportal.domain.Discipline
import edu.hneu.studentsportal.domain.DisciplineMark
import edu.hneu.studentsportal.enums.DisciplineType
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext

class LimitSemesterDisciplinesValidatorTest extends Specification {

    def context = Mock(ConstraintValidatorContext)
    def annotation = Mock(LimitSemesterDisciplines)

    LimitSemesterDisciplinesValidator validator = new LimitSemesterDisciplinesValidator(
            permittedCount: 2,
            type: DisciplineType.MAGMAYNOR
    )

    def 'should initialize validator permitted count'() {
        given:
            annotation.count() >> 2
        when:
            validator.initialize(annotation)
        then:
            1 * annotation.count()
    }

    def 'should initialize validator type'() {
        given:
            annotation.type() >> DisciplineType.MAGMAYNOR
        when:
            validator.initialize(annotation)
        then:
            1 * annotation.type()
    }

    def 'should validate when disciplines marks list is empty'() {
        when:
            def isValid = validator.isValid([], context)
        then:
            isValid
    }

    def 'should invalidate when disciplines marks list contains more then permitted magmaynors count '() {
        given:
            def disciplines = [
                    new DisciplineMark(discipline: new Discipline('Mag-Maynor 1', DisciplineType.MAGMAYNOR, 1, 1)),
                    new DisciplineMark(discipline: new Discipline('Mag-Maynor 2', DisciplineType.MAGMAYNOR, 1, 1)),
                    new DisciplineMark(discipline: new Discipline('Mag-Maynor 3', DisciplineType.MAGMAYNOR, 1, 1)),
            ]
        when:
            def isValid = validator.isValid(disciplines, context)
        then:
            !isValid
    }

    def 'should validate when disciplines marks list contains permitted magmaynors count '() {
        given:
            def disciplines = [
                    new DisciplineMark(discipline: new Discipline('Mag-Maynor 1', DisciplineType.MAGMAYNOR, 1, 1)),
                    new DisciplineMark(discipline: new Discipline('Mag-Maynor 2', DisciplineType.MAGMAYNOR, 1, 1)),
                    new DisciplineMark(discipline: new Discipline('Regular 1', DisciplineType.REGULAR, 1, 1)),
                    new DisciplineMark(discipline: new Discipline('Regular 2', DisciplineType.REGULAR, 1, 1))
            ]
        when:
            def isValid = validator.isValid(disciplines, context)
        then:
            isValid
    }
}
