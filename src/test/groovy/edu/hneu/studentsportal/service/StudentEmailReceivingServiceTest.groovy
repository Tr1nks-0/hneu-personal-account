package edu.hneu.studentsportal.service

import edu.hneu.studentsportal.domain.Group
import edu.hneu.studentsportal.domain.Student
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestOperations
import spock.lang.Specification

class StudentEmailReceivingServiceTest extends Specification {

    static final String EMAIL_INTEGRATION_SERVICE_URL = 'test.email.service'
    static final String STUDENT_EMAIL = 'student@email'
    static final String REQUEST_EMAIL_URL = 'test.email.service/EmailToOutController?name=олександр&surname=роздольський&groupId=8.04.51.16.01'
    static final String ERROR_MESSAGE = 'error message'

    def messageServiceMock = Mock(MessageService)
    def studentMock = Mock(Student)
    def groupMock = Mock(Group)
    def restTemplateMock = Mock(RestOperations)
    def restEntityMock = Mock(ResponseEntity)

    def studentEmailReceivingService = new StudentEmailReceivingService(
        messageService: messageServiceMock,
        emailsIntegrationServiceUrl: EMAIL_INTEGRATION_SERVICE_URL,
        restTemplate: restTemplateMock
    )

    def setup() {
        studentMock.getName() >> 'Олександр'
        studentMock.getSurname() >> 'Роздольський'
        studentMock.getGroup() >> groupMock
        groupMock.getName() >> '8.04.51.16.01'
        restTemplateMock.getForEntity(REQUEST_EMAIL_URL, String.class) >> restEntityMock
    }

    def 'should return student email when service responded correctly'() {
        given:
            restEntityMock.getBody() >> STUDENT_EMAIL
        when:
            def email = studentEmailReceivingService.receiveStudentEmail(studentMock)
        then:
            STUDENT_EMAIL == email
    }

    def 'should throw exception when student email was not found'() {
        given:
            restEntityMock.getBody() >> null
            messageServiceMock.emailNotFoundForStudent(*_) >> ERROR_MESSAGE
        when:
            studentEmailReceivingService.receiveStudentEmail(studentMock)
        then:
            IllegalArgumentException ex = thrown()
            ex.message == ERROR_MESSAGE
    }
}
