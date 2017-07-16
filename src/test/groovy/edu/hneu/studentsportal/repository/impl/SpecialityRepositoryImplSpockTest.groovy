package edu.hneu.studentsportal.repository.impl

import edu.hneu.studentsportal.domain.Faculty
import edu.hneu.studentsportal.domain.Speciality
import edu.hneu.studentsportal.repository.SpecialityRepository
import spock.lang.Specification

class SpecialityRepositoryImplSpockTest extends Specification {

    static final int SPECIALITY_ID = 1
    static final int FACULTY_ID = 1

    def specialityRepositoryMock = Mock(SpecialityRepository)
    def facultyMock = Mock(Faculty)
    def specialityMock = Mock(Speciality)

    def specialityRepository = new SpecialityRepositoryImpl(
            specialityRepository: specialityRepositoryMock
    )

    def 'should find speciality by id'() {
        given:
            specialityRepositoryMock.findById(SPECIALITY_ID) >> Optional.of(specialityMock)
        when:
            def speciality = specialityRepository.findByIdOrDefault(SPECIALITY_ID, facultyMock)
        then:
            specialityMock ==  speciality
    }

    def 'should find first speciality for faculty when expected speciality was not found'() {
        given:
            specialityRepositoryMock.findById(SPECIALITY_ID) >> Optional.empty()
            facultyMock.getId() >> FACULTY_ID
            specialityRepositoryMock.findFirstSpecialityForFaculty(FACULTY_ID) >> specialityMock
        when:
            def speciality = specialityRepository.findByIdOrDefault(SPECIALITY_ID, facultyMock)
        then:
            specialityMock ==  speciality
    }
}
