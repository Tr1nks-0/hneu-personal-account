package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Student;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import javax.annotation.Resource;

import static com.sun.tools.javac.util.Log.format;

@Service
public class StudentEmailReceivingService {

    @Resource
    private MessageService messageService;
    @Resource
    private RestOperations restTemplate;
    @Value("${integration.service.emails.url}")
    public String emailsIntegrationServiceUrl;

    public String receiveStudentEmail(Student student) {
        String formattedName = student.getName().toLowerCase().split(" ")[0];
        String formatterSurname = student.getSurname().toLowerCase().trim();
        String groupName = student.getGroup().getName();
        String url = format("%s/EmailToOutController?name=%s&surname=%s&groupId=%s", emailsIntegrationServiceUrl, formattedName, formatterSurname, groupName);
        return Try.of(() -> restTemplate.getForEntity(url, String.class))
                .map(ResponseEntity::getBody)
                .map(String::toLowerCase)
                .getOrElseThrow(() -> new IllegalArgumentException(messageService.emailNotFoundForStudent(formattedName + " " + formatterSurname)));
    }
}
