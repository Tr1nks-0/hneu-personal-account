package edu.hneu.studentsportal.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static java.util.Objects.nonNull;

@Log4j
@Controller
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "redirect:login";
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam(required = false) String error, Principal principal, Model model) {
        if (nonNull(error))
            model.addAttribute("error", true);
        else if (nonNull(principal))
            return "redirect:/account";
        return "login";
    }
}