package edu.hneu.studentsportal.controller.student;

import edu.hneu.studentsportal.domain.Student;
import edu.hneu.studentsportal.domain.dto.schedule.Schedule;
import edu.hneu.studentsportal.domain.dto.schedule.ScheduleElement;
import edu.hneu.studentsportal.service.MessageService;
import edu.hneu.studentsportal.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import java.util.Map;

import static java.util.Objects.nonNull;

@Log4j
@Controller
@RequestMapping("/account/schedule")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleAccountController {

    private final ScheduleService scheduleService;
    private final MessageService messageService;

    @GetMapping
    public String schedule(@ModelAttribute("profile") Student profile, @RequestParam(required = false) Long week, Model model) {
        week = scheduleService.getWeekOrDefault(week);
        model.addAttribute("week", week);
        model.addAttribute("title", "top.menu.schedule");

        Schedule schedule = scheduleService.load(profile.getGroup().getId(), week, profile.getScheduleStudentId());
        if (nonNull(schedule)) {
            model.addAttribute("pairs", scheduleService.getPairs(schedule));
            model.addAttribute("pairsCountPerDay", scheduleService.getPairsCountPerDay(schedule));
            model.addAttribute("days", schedule.getWeek().getDay());
        } else {
            model.addAttribute("error", messageService.somethingWentWrong());
        }
        return "student/schedule";
    }
}
