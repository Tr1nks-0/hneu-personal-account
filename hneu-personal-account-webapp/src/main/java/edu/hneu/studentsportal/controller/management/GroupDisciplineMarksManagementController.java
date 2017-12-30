package edu.hneu.studentsportal.controller.management;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.DisciplineMark;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.dto.DisciplineMarksDTO;
import edu.hneu.studentsportal.repository.DisciplineMarkRepository;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static edu.hneu.studentsportal.controller.ControllerConstants.MANAGE_GROUPS_URL;
import static org.apache.commons.lang.BooleanUtils.isFalse;

@Log4j
@Controller
@RequestMapping(MANAGE_GROUPS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupDisciplineMarksManagementController extends AbstractManagementController {

    private final DisciplineRepository disciplineRepository;
    private final DisciplineMarkRepository disciplineMarkRepository;
    private final GroupRepository groupRepository;

    @GetMapping("/{groupId}/disciplines/{disciplineId}")
    public String getDisciplines(@PathVariable Long groupId,
                                 @PathVariable Long disciplineId,
                                 @RequestParam(defaultValue = "1") Long course,
                                 @RequestParam(defaultValue = "1") Long semester,
                                 Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        Discipline discipline = disciplineRepository.findById(disciplineId).orElseThrow(IllegalArgumentException::new);
        List<DisciplineMark> marks = disciplineMarkRepository.findByDiscipline(discipline);
        model.addAttribute("group", group);
        model.addAttribute("discipline", discipline);
        model.addAttribute("disciplineMarks", new DisciplineMarksDTO(marks));
        model.addAttribute("course", course);
        model.addAttribute("semester", semester);
        model.addAttribute("title", "management-marks");
        return "management/group-discipline-marks-page";
    }

    @PostMapping("/{groupId}/disciplines/{disciplineId}")
    public String saveMarks(@ModelAttribute DisciplineMarksDTO disciplineMarks,
                            @PathVariable Long groupId,
                            @RequestParam(defaultValue = "1") String course,
                            @RequestParam(defaultValue = "1") String semester,
                            RedirectAttributes redirectAttributes) throws URISyntaxException {
        List<DisciplineMark> marksToSave = new ArrayList<>();
        disciplineMarks.getMarks().forEach(mark -> {
            DisciplineMark disciplineMark = disciplineMarkRepository.getOne(mark.getId());
            if (isFalse(StringUtils.equalsIgnoreCase(disciplineMark.getMark(), mark.getMark()))) {
                disciplineMark.setMark(mark.getMark());
                marksToSave.add(disciplineMark);
            }
        });
        if (isFalse(marksToSave.isEmpty())) {
            disciplineMarkRepository.save(marksToSave);
            redirectAttributes.addFlashAttribute("success", "success.update.marks");
        }
        return new URIBuilder("redirect:/management/groups/" + groupId + "/disciplines")
                .addParameter("course", course).addParameter("semester", semester).toString();
    }

    @Override
    public String baseUrl() {
        return MANAGE_GROUPS_URL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}