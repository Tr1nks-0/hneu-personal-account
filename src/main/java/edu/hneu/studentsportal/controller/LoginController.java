package edu.hneu.studentsportal.controller;

import edu.hneu.studentsportal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Objects;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login() {
        return "redirect:login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) final String error, final Principal principal, final Model model) {
        if (Objects.nonNull(principal))
            return "redirect:/account";
        if (Objects.nonNull(error))
            model.addAttribute("error", Boolean.TRUE);
        return "login";
    }

}