package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.dao.GroupDao;
import edu.hneu.studentsportal.model.Group;
import edu.hneu.studentsportal.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DefaultScheduleService implements ScheduleService {

    private static final String ID_REGEX = "id=\".+\"";
    private static final String NAME_REGEX = "<displayName>.+</displayName>";
    private static final String FACULTIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=faculties&auth=test";
    private static final String SPECIALITIES_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=specialities&facultyid=%s&auth=test";
    private static final String GROUPS_URL = "http://services.ksue.edu.ua:8081/schedule/xmlmetadata?q=groups&facultyid=%s&specialityid=%s&course=%s&auth=test";

    @Autowired
    private GroupDao groupDao;

    @Override
    public void downloadGroups() {
        List<String> facultyIds = getListOfIds(getStringResponse(FACULTIES_URL));
        for(String facultyId : facultyIds) {
            List<String> specialityIds =  getListOfIds(getStringResponse(String.format(SPECIALITIES_URL, facultyId)));
            for(String specialityId : specialityIds) {
                for(int course = 1; course <= 6; course++) {
                    String response = getStringResponse(String.format(GROUPS_URL, facultyId, specialityId, course));
                    List<String> groupIds = getListOfIds(response);
                    List<String> groupName = getListOfNames(response);
                    for (int i = 0; i < groupIds.size(); i++) {
                        System.out.println("new groups");
                        groupDao.save(new Group(groupIds.get(i), groupName.get(i)));
                    }
                }
            }
        }
    }

    private String getStringResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    private List<String> getListOfIds(String stringToParse) {
        Matcher matcher = Pattern.compile(ID_REGEX).matcher(stringToParse);
        List<String> ids = new ArrayList<>();
        while(matcher.find()) {
            String parsedId = matcher.group();
            String idValue = parsedId.substring(4, parsedId.length() - 1);
            ids.add(idValue);
        }
        return ids;
    }

    private List<String> getListOfNames(String stringToParse) {
        Matcher matcher = Pattern.compile(NAME_REGEX).matcher(stringToParse);
        List<String> names = new ArrayList<>();
        while(matcher.find()) {
            String parsedName = matcher.group();
            String nameValue = parsedName.substring(13, parsedName.length() - 14);
            names.add(nameValue);
        }
        return names;
    }


}
