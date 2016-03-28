package com.rozdolskyi.traininghneu.controller;

import com.rozdolskyi.traininghneu.model.RegisterForm;
import com.rozdolskyi.traininghneu.model.StudentProfile;
import com.rozdolskyi.traininghneu.model.Tag;
import com.rozdolskyi.traininghneu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

	@Autowired
	private StudentService studentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("register", "registerForm", new RegisterForm());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		model.addObject("profile", new RegisterForm());
		model.setViewName("register");
		return model;
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Tag> getCountryList(@RequestParam("term") String query) throws UnsupportedEncodingException {
		return simulateSearchResult(new String(query.getBytes("ISO-8859-1"), "UTF-8"));
	}

	private List<Tag> simulateSearchResult(String tagName) {
		List<Tag> result = new ArrayList<>();
		for (StudentProfile profile : studentService.findAll()) {
			if (profile.toString().toLowerCase().contains(tagName.toLowerCase())) {
				result.add(new Tag(profile.getId(), profile.toString()));
			}
		}
		return result;
	}
	
}