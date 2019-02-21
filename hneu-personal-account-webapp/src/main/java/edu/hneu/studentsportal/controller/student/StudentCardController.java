package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.studentcart.CourseData;
import edu.hneu.studentsportal.service.StudentCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


@Log4j
@Controller
@RequestMapping("/card")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentCardController {
    private static final String[] NUMBER_NAMES = {"ПЕРШИЙ", "ДРУГИЙ", "ТРЕТІЙ", "ЧЕТВЕРТИЙ"};
    private static final double HOURS_PER_CREDIT = 30;

    @Resource
    private StudentCardService studentCardService;


    @GetMapping("{id}")
    public String getForStudent(@PathVariable("id") long studentId, Model model) {
        Student student = studentCardService.getStudent(studentId);
        List<CourseData> studentTableData = studentCardService.getTableData(student);

        model.addAttribute("numberNames", NUMBER_NAMES);
        model.addAttribute("tableData", studentTableData);
        model.addAttribute("student", student);
        model.addAttribute("hoursPerCredit", HOURS_PER_CREDIT);
        return "report/html/student-card";
    }

}
