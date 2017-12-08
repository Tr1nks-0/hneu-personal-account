package edu.hneu.studentsportal.service;

import javaslang.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentEmailReceivingService {

    private final MessageService messageService;
    private final RestOperations restTemplate;

    @Value("${integration.service.emails.url}")
    public String emailsIntegrationServiceUrl;

    public String receiveStudentEmail(String name, String surname, String groupName) {
        String formattedName = name.toLowerCase().split(" ")[0];
        String formatterSurname = surname.toLowerCase().trim();
        String url = format("%s/EmailToOutController?name=%s&surname=%s&groupId=%s", emailsIntegrationServiceUrl, formattedName, formatterSurname, groupName);
        Try<String> email = Try.of(() -> restTemplate.getForEntity(url, String.class))
                .map(ResponseEntity::getBody)
                .map(String::toLowerCase);
        if (email.isSuccess() && email.get().contains("@")) {
            return email.get();
        } else {
            throw new IllegalArgumentException(messageService.emailNotFoundForStudent(formattedName + " " + formatterSurname));
        }
    }
}
