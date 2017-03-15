package edu.hneu.studentsportal.listener;

import edu.hneu.studentsportal.entity.Group;
import edu.hneu.studentsportal.repository.GroupRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.entity.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;

import javax.annotation.Resource;

@Component
public class InitializationAdminUserOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(InitializationAdminUserOnStartupListener.class);

    private static final String ADMIN_USER_ID = "oleksandr.rozdolskyi2012@gmail.com";

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @Resource
    private GroupRepository groupRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        final User admin = new User();
        admin.setId(ADMIN_USER_ID);
        admin.setRole(UserRole.ADMIN);
        userService.save(admin);
        LOG.info("Default admin was created!");

        groupRepository.save(new Group(21382, "8.04.51.16.04"));

        // studentService.refreshMarks();
        // LOG.info("All marks refreshed");
    }

}
