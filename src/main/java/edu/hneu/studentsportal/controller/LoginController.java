package edu.hneu.studentsportal.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.hneu.studentsportal.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "redirect:login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) final String error, final Principal principal, final Model model) {
        if(Objects.nonNull(principal)){
            return "redirect:/account";
        }
        if (Objects.nonNull(error)) {
            model.addAttribute("error", Boolean.TRUE);
        }
        return "login";
    }

}