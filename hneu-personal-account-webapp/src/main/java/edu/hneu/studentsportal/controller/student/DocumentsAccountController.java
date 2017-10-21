package edu.hneu.studentsportal.controller.student;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j
@Controller
@RequestMapping("/account")
public class DocumentsAccountController {

    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("title", "top.menu.documents");
        return "student/documents";
    }


}
