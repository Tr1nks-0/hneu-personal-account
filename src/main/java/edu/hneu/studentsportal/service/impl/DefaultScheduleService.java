package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.ScheduleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DefaultScheduleService implements ScheduleService {

    private static final Logger LOG = Logger.getLogger(DefaultScheduleService.class.getName());

    private static final String ID_REGEX = "id=\".+\"";
    private static final String NAME_REGEX = "<displayName>.+</displayName>";
    private static final String FACULTIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=faculties&auth=test";
    private static final String SPECIALITIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=specialities&facultyid=%s&auth=test";
    private static final String GROUPS_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=groups&facultyid=%s&specialityid=%s&course=%s&auth=test";

    @Resource
    private GroupRepository groupRepository;

    @Override
    public void downloadGroups() {
        LOG.info("Groups downloading started");
        final List<String> facultyIds = getListOfIds(getStringResponse(FACULTIES_URL));
        for(final String facultyId : facultyIds) {
            final List<String> specialityIds =  getListOfIds(getStringResponse(String.format(SPECIALITIES_URL, facultyId)));
            for(final String specialityId : specialityIds) {
                for(int course = 1; course <= 6; course++) {
                    final String response = getStringResponse(String.format(GROUPS_URL, facultyId, specialityId, course));
                    final List<String> groupIds = getListOfIds(response);
                    final List<String> groupName = getListOfNames(response);
                    for (int i = 0; i < groupIds.size(); i++) {
                        groupRepository.save(new Group(Integer.valueOf(groupIds.get(i)), groupName.get(i)));
                    }
                }
            }
        }
        LOG.info("Groups downloading finished");
    }

    private String getStringResponse(final String url) {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    private List<String> getListOfIds(final String stringToParse) {
        final Matcher matcher = Pattern.compile(ID_REGEX).matcher(stringToParse);
        final List<String> ids = new ArrayList<>();
        while(matcher.find()) {
            final String parsedId = matcher.group();
            final String idValue = parsedId.substring(4, parsedId.length() - 1);
            ids.add(idValue);
        }
        return ids;
    }

    private List<String> getListOfNames(final String stringToParse) {
        final Matcher matcher = Pattern.compile(NAME_REGEX).matcher(stringToParse);
        final List<String> names = new ArrayList<>();
        while(matcher.find()) {
            final String parsedName = matcher.group();
            final String nameValue = parsedName.substring(13, parsedName.length() - 14);
            names.add(nameValue);
        }
        return names;
    }


}
