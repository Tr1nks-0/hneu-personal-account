package edu.hneu.studentsportal.service

import edu.hneu.studentsportal.repository.StudentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestOperations
import spock.lang.Specification

class StudentServiceSpockTest extends Specification {

    static final String EMAIL_INTEGRATION_SERVICE_URL = 'test.email.service'
    static final String STUDENT_EMAIL = 'student@email'
    static
    final String REQUEST_EMAIL_URL = 'test.email.service/EmailToOutController?name=олександр&surname=роздольський&groupId=8.04.51.16.01'
    static final String ERROR_MESSAGE = 'error message'

    static final String STUDENT_NAME = 'Олександр'
    static final String STUDENT_SURNAME = 'Роздольський'
    static final String STUDENT_GROUP_NAME = '8.04.51.16.01'

    def messageServiceMock = Mock(MessageService)
    def restTemplateMock = Mock(RestOperations)
    def studentRepositoryMock = Mock(StudentRepository)
    def userServiceMock = Mock(UserService)
    def fileServiceMock = Mock(FileService)
    def disciplineMarkServiceMock = Mock(DisciplineMarkService)
    def restEntityMock = Mock(ResponseEntity)

    def studentEmailReceivingService = new StudentService(
            messageServiceMock,
            restTemplateMock,
            studentRepositoryMock,
            userServiceMock,
            fileServiceMock,
            disciplineMarkServiceMock
    )

    def setup() {
        studentEmailReceivingService.emailsIntegrationServiceUrl = EMAIL_INTEGRATION_SERVICE_URL
        restTemplateMock.getForEntity(REQUEST_EMAIL_URL, String.class) >> restEntityMock
    }

    def 'should return student email when service responded correctly'() {
        given:
        restEntityMock.getBody() >> STUDENT_EMAIL
        when:
        def email = studentEmailReceivingService.receiveStudentEmail(STUDENT_NAME, STUDENT_SURNAME, STUDENT_GROUP_NAME)
        then:
        STUDENT_EMAIL == email
    }

    def 'should throw exception when student email was not found'() {
        given:
        restEntityMock.getBody() >> null
        messageServiceMock.emailNotFoundForStudent(*_) >> ERROR_MESSAGE
        when:
        studentEmailReceivingService.receiveStudentEmail(STUDENT_NAME, STUDENT_SURNAME, STUDENT_GROUP_NAME)
        then:
        IllegalArgumentException ex = thrown()
        ex.message == ERROR_MESSAGE
    }
}
