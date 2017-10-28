package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/students/{id}/photo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentPhotoController {

    private final StudentRepository studentRepository;

    @GetMapping
    @SneakyThrows
    public void getPhoto(@PathVariable long id, HttpServletResponse response) {
        Student student = studentRepository.findOne(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(student.getPhoto());
        outputStream.close();
    }
}
