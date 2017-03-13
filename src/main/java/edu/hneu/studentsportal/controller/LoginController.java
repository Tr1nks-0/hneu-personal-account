package edu.hneu.studentsportal.controller;

import static java.util.Objects.nonNull;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "redirect:login";
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam(required = false) String error, Principal principal, Model model) {
        if (nonNull(principal))
            return "redirect:/account";
        if (nonNull(error))
            model.addAttribute("error", true);
        return "login";
    }

}